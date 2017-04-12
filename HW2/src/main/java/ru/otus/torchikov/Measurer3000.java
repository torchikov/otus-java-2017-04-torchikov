package ru.otus.torchikov;

import com.sun.istack.internal.Nullable;

import java.lang.management.ManagementFactory;
import java.util.*;

/**
 * Created by sergei on 10.04.17.
 */
public class Measurer3000 {
    private final int repeatCount = 20; //Кол-во итераций
    private final int elementCount = 100_000; //Кол-во элементов
    private long memoryBefore;
    private long memoryAfter;
    private Map<Long, Integer> results = new HashMap<>();


    public void printObjectSize(ObjectTypeEnum objectType) {
        Object[] array = new Object[elementCount];
        for (int i = 0; i < repeatCount; i++) {
            startMeasure();
            for (int j = 0; j < elementCount; j++) {
                array[j] = getObjectForMeasure(objectType);
            }
            stopMeasure();
            assert array[0] != null;
            array = new Object[elementCount];
        }
        System.out.println("Size of " + objectType.getDescription() + " is " + getResult() + " bytes");

    }

    public void printConteinerSize(int elementsInContainer, ContainerTypeEnum containerType, @Nullable ObjectTypeEnum objectTypeInContainer) {
        Object[] array = new Object[elementCount];
        for (int i = 0; i < repeatCount; i++) {
            startMeasure();
            for (int j = 0; j < elementCount; j++) {
                array[j] = getContainerForMeasure(elementsInContainer, containerType, objectTypeInContainer);
            }
            stopMeasure();
            assert array[0] != null;
            array = new Object[elementCount];
        }
        StringBuilder message = new StringBuilder("Size of ").append(containerType.getDescription()).append(" for ").append(elementsInContainer).append(" elements ");
        if (Objects.isNull(objectTypeInContainer)) {
            message.append("with no elements is ").append(getResult()).append(" bytes");
        } else {
            message.append("with elemetns of ").append(objectTypeInContainer.getDescription()).append(" is ").append(getResult()).append(" bytes");
        }
        System.out.println(message.toString());
    }

    private Object getContainerForMeasure(int elementCount, ContainerTypeEnum containerType, @Nullable ObjectTypeEnum objectType) {
        if (containerType == ContainerTypeEnum.ARRAY) {
            return getArray(elementCount, objectType);
        } else if (containerType == ContainerTypeEnum.ARRAY_LIST) {
            return getArrayList(elementCount, objectType);
        } else if (containerType == ContainerTypeEnum.HASH_SET) {
            return getHashSet(elementCount, objectType);
        } else if (containerType == ContainerTypeEnum.LINKED_LIST) {
            return getLikedList(elementCount, objectType);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Object getObjectForMeasure(ObjectTypeEnum objectType) {
        if (objectType == ObjectTypeEnum.STRING_WITH_STRING_POOL) {
            return getEmptyStringWithStringPool();
        } else if (objectType == ObjectTypeEnum.STRING_WITHOUT_STRING_POOL) {
            return getEmptyStringWithoutStringPool();
        } else if (objectType == ObjectTypeEnum.INTEGER) {
            return getInteger();
        } else if (objectType == ObjectTypeEnum.OBJECT) {
            return getObject();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void addToResult() {
        long size = getObjectSizeInBytes();
        int times = 1;
        if (Objects.nonNull(results.get(size))) {
            times = results.get(size) + 1;
        }
        results.merge(size, times, (v1, v2) -> v2);
    }

    private long getResult() {
        long size = 0;
        int oldMaxTimes = 0;
        for (Map.Entry<Long, Integer> entry : results.entrySet()) {
            int maxTimes = entry.getValue();
            if (maxTimes > oldMaxTimes) {
                oldMaxTimes = maxTimes;
                size = entry.getKey();
            }
        }
        results = new HashMap<>();
        return size;
    }

    private void startMeasure() {
        collectGarbage();
        memoryBefore = getUsedMemory();
    }


    private void stopMeasure() {
        collectGarbage();
        memoryAfter = getUsedMemory();
        addToResult();
    }

    private long getObjectSizeInBytes() {
        return Math.round((memoryAfter - memoryBefore) / elementCount);
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

    private String getEmptyStringWithStringPool() {
        return new String("");
    }

    private String getEmptyStringWithoutStringPool() {
        return new String(new char[0]);
    }

    private Object getObject() {
        return new Object();
    }

    private Integer getInteger() {
        return new Integer(1);
    }


    private Object[] getArray(int arraySize, @Nullable ObjectTypeEnum objectType) {
        if (Objects.isNull(objectType)) {
            return new Object[arraySize];
        } else {
            Object[] result = new Object[arraySize];
            for (int i = 0; i < arraySize; i++) {
                result[i] = getObjectForMeasure(objectType);
            }
            return result;
        }
    }

    private ArrayList<Object> getArrayList(int elementCount, @Nullable ObjectTypeEnum objectType) {
        if (Objects.isNull(objectType)) {
            return new ArrayList<>();
        } else {
            ArrayList<Object> result = new ArrayList<>();
            for (int i = 0; i < elementCount; i++) {
                result.add(getObjectForMeasure(objectType));
            }
            return result;
        }
    }

    private HashSet<Object> getHashSet(int elementCount, @Nullable ObjectTypeEnum objectType) {
        if (Objects.isNull(objectType)) {
            return new HashSet<>();
        } else {
            HashSet<Object> result = new HashSet<>();
            for (int i = 0; i < elementCount; i++) {
                result.add(getObjectForMeasure(objectType));
            }
            return result;
        }
    }

    private LinkedList<Object> getLikedList(int elementCount, @Nullable ObjectTypeEnum objectType) {
        if (Objects.isNull(getObject())) {
            return new LinkedList<>();
        } else {
            LinkedList<Object> result = new LinkedList<>();
            for (int i = 0; i < elementCount; i++) {
                result.add(getObjectForMeasure(objectType));
            }
            return result;
        }
    }


}
