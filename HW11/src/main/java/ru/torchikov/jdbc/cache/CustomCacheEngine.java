package ru.torchikov.jdbc.cache;

import com.sun.istack.internal.Nullable;
import ru.torchikov.jdbc.datasets.UserDataSet;

import java.lang.ref.SoftReference;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

/**
 * Created by sergei on 21.06.17.
 */
public class CustomCacheEngine implements CacheEngine<UserDataSet>{

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private int hits = 0;
    private int miss = 0;

    private final Map<Long, SoftReference<CacheElement<UserDataSet>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    public CustomCacheEngine(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    @Override
    public synchronized void put(UserDataSet dataSet) {
        if (elements.size() == maxElements) {
            Long firsKey = elements.keySet().iterator().next();
            elements.remove(firsKey);
        }
        CacheElement<UserDataSet> cacheElement = new CacheElement<>(dataSet);
        elements.put(dataSet.getId(), new SoftReference<>(cacheElement));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(dataSet.getId(), lifeElement -> lifeElement.getCreationTime().plusMillis(lifeTimeMs));
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                registerIdleTimer(idleTimeMs);
            }
        }
    }

    private void registerIdleTimer(long idleTime) {
        TimerTask idleTimerTask = new TimerTask() {
            @Override
            public void run() {
                for (Long key : elements.keySet()) {
                    CacheElement<UserDataSet> cacheElement = elements.get(key).get();
                    if (cacheElement == null) {
                        continue;
                    }
                    if (cacheElement.getLastAccessTime().plusMillis(idleTime).isBefore(Instant.now())) {
                        elements.remove(key);
                    }
                }
            }
        };
        timer.schedule(idleTimerTask, idleTime, idleTime);
    }

    @Override
    @Nullable
    public synchronized UserDataSet get(Long key) {
        SoftReference<CacheElement<UserDataSet>> cacheElement = elements.get(key);

        if (cacheElement == null) {
            miss++;
            return null;
        }
        CacheElement<UserDataSet> element = cacheElement.get();

        if (element == null) {
            miss++;
            return null;
        } else {
            hits++;
            return element.getValue();
        }

    }

    @Override
    public synchronized void remove(Long key) {
        elements.remove(key);
    }

    @Override
    public synchronized int getHitCount() {
        return this.hits;
    }

    @Override
    public synchronized  int getMissCount() {
        return this.miss;
    }

    @Override
    public void dispose() {
        elements.clear();
        hits = 0;
        miss = 0;
    }

    private TimerTask getTimerTask(final Long key, Function<CacheElement<UserDataSet>, Instant> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<UserDataSet> checkedElement = elements.get(key).get();
                if (checkedElement == null ||
                        (timeFunction.apply(checkedElement).isBefore(Instant.now()))) {
                    elements.remove(key);
                }
            }
        };
    }
}
