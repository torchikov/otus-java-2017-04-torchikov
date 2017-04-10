package ru.otus.torchikov;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by sergei on 10.04.17.
 */
public class Measurer3000 {
    private  long memoryBefore;
    private  long memoryAfter;
    private  Map<Long, Integer> results = new HashMap<>();
    private  Class<?> clazz;
    private final int count;

    public Measurer3000(int elementCount) {
        this.count = elementCount;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    private void addToResult() {
        long size = getObjectSizeInBytes();
        int times = 1;
        if (Objects.nonNull(results.get(size))) {
            times = results.get(size) + 1;
        }
        results.merge(size, times, (v1, v2) -> v2);
    }

    public long getResult() {
        long size = 0;
        int oldMaxTimes = 0;
        for (Map.Entry<Long, Integer> entry : results.entrySet()) {
            int maxTimes = entry.getValue();
            if (maxTimes > oldMaxTimes) {
                oldMaxTimes = maxTimes;
                size = entry.getKey();
            }
        }
        return size;
    }

    public void startMeasure() {
        collectGarbage();
        memoryBefore = getUsedMemory();
    }


    public void stopMeasure() {
        collectGarbage();
        memoryAfter = getUsedMemory();
        addToResult();
    }

    private long getObjectSizeInBytes() {
        return Math.round((memoryAfter - memoryBefore) / count);
    }

    private void collectGarbage() {
        long memory1 = getUsedMemory();
        long memory2 = Long.MAX_VALUE;
        for (int i = 0; (memory1 < memory2) && (i < 500); i++) {
            System.runFinalization();
            System.gc();

            memory2 = memory1;
            memory1 = getUsedMemory();
        }
    }

    private long getUsedMemory() {
        return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();

    }
}
