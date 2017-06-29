package ru.torchikov.beans;

/**
 * Created by sergei on 28.06.17.
 */
public interface CacheManagerMBean {

    int getHits();

    int getMisses();

    int getMaxElementsCount();

    void setMaxElementsCount(int maxElementsCount);

    long getLifeTimeMs();

    void setLifeTimeMs(long lifeTimeMs);

    long getIdleTimeMs();

    void setIdleTimeMs(long idleTimeMs);

    boolean isEternal();

    void dispose();
}
