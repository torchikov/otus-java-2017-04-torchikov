package ru.torchikov.beans;

import ru.torchikov.jdbc.cache.CacheEngine;

/**
 * Created by sergei on 28.06.17.
 *
 */
public class CacheManager implements CacheManagerMBean {

    private final CacheEngine<?> cacheEngine;

    public CacheManager(CacheEngine<?> cacheEngine) {
        this.cacheEngine = cacheEngine;
    }

    @Override
    public int getHits() {
        return cacheEngine.getHitCount();
    }

    @Override
    public int getMisses() {
        return cacheEngine.getMissCount();
    }

    @Override
    public int getMaxElementsCount() {
        return cacheEngine.getMaxElements();
    }

    @Override
    public void setMaxElementsCount(int maxElementsCount) {
        cacheEngine.setMaxElements(maxElementsCount);
    }

    @Override
    public long getLifeTimeMs() {
        return cacheEngine.getLifeTimeMs();
    }

    @Override
    public void setLifeTimeMs(long lifeTimeMs) {
        cacheEngine.setLifeTimeMs(lifeTimeMs);
    }

    @Override
    public long getIdleTimeMs() {
        return cacheEngine.getIdleTimeMs();
    }

    @Override
    public void setIdleTimeMs(long idleTimeMs) {
        cacheEngine.setIdleTimeMs(idleTimeMs);
    }

    @Override
    public boolean isEternal() {
        return cacheEngine.isEtermal();
    }

    @Override
    public void dispose() {
        cacheEngine.dispose();
    }
}
