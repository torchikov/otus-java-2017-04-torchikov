package ru.torchikov.jdbc.cache;

import com.sun.istack.internal.Nullable;

/**
 * Created by sergei on 21.06.17.
 */
public interface CacheEngine<V> {
    void put(V value);

    @Nullable
    V get(Long id);

    void remove(Long id);

    int getHitCount();

    int getMissCount();

    int getMaxElements();

    void setMaxElements(int maxElements);

    long getLifeTimeMs();

    void setLifeTimeMs(long lifeTimeMs);

    long getIdleTimeMs();

    void setIdleTimeMs(long idleTimeMs);

    void dispose();

    boolean isEtermal();
}
