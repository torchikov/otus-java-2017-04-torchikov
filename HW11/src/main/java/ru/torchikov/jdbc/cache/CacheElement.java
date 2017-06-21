package ru.torchikov.jdbc.cache;

import java.time.Instant;

/**
 * Created by sergei on 21.06.17.
 */
public class CacheElement<V> {
    private final V value;
    private final Instant creationTime;
    private Instant lastAccessTime;

    public CacheElement(V value) {
        this.value = value;
        this.creationTime = Instant.now();
        this.lastAccessTime = this.creationTime;
    }

    protected Instant getCurrentTime() {
        return Instant.now();
    }

    public V getValue() {
        return value;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public Instant getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        this.lastAccessTime = Instant.now();
    }
}
