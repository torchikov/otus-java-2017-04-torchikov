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

    void dispose();
}
