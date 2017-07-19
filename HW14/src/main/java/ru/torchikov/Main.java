package ru.torchikov;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by sergei on 15.07.17.
 *
 */
public class Main {
    public static void main(String[] args) {
        int[] array = {5, 12, 78, 4, 6, 32, 10, 6, 15, 99};
        int[] result = Arrays.stream(array)
                .parallel()
                .sorted()
                .toArray();
        System.out.println(Arrays.toString(result));

        //Совсем уж просто:
        Arrays.parallelSort(array);
        System.out.println(Arrays.toString(array));

        Random random = new Random();
        Integer[] arrayToSort = new Integer[10_000];
        for (int i = 0; i < 10_000; i++) {
            arrayToSort[i] = random.nextInt();
        }
        ArraySorterTask<Integer> task = new ArraySorterTask<>(arrayToSort);
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Integer[] sortedArray = pool.invoke(task);
        System.out.println(Arrays.toString(sortedArray));
    }

    private static class ArraySorterTask<T extends Comparable<? super T>> extends RecursiveTask<T[]> {
        private static final int THRESHOLD = 2;

        private T[] array;

        ArraySorterTask(T[] array) {
            this.array = array;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected T[] compute() {
            if (array.length <= THRESHOLD) {
                Arrays.sort(array);
                return array;
            } else {
                ArraySorterTask<T> task1 = new ArraySorterTask<>(Arrays.copyOfRange(array, 0, array.length / 2));
                ArraySorterTask<T> task2 = new ArraySorterTask<>(Arrays.copyOfRange(array, array.length / 2, array.length));
                invokeAll(task1, task2);

                T[] array1 = task1.join();
                T[] array2 = task2.join();
                T[] mergedArray = (T[]) Array.newInstance(array1[0].getClass(), array1.length + array2.length);

                merge(array1, array2, mergedArray);
                return mergedArray;
            }
        }

        private void merge(T[] array1, T[] array2, T[] mergedArray) {
            int firstArrayIndex = 0;
            int secondArrayIndex = 0;
            int mergedArrayIndex = 0;

            while ((firstArrayIndex < array1.length) && (secondArrayIndex < array2.length)) {
                if (array1[firstArrayIndex].compareTo(array2[secondArrayIndex]) < 0) {
                    mergedArray[mergedArrayIndex] = array1[firstArrayIndex++];
                } else {
                    mergedArray[mergedArrayIndex] = array2[secondArrayIndex++];
                }
                mergedArrayIndex++;
            }

            if (firstArrayIndex == array1.length) {
                for (int i = secondArrayIndex; i < array2.length; i++) {
                    mergedArray[mergedArrayIndex++] = array2[i];
                }
            } else {
                for (int i = firstArrayIndex; i < array1.length; i++) {
                    mergedArray[mergedArrayIndex++] = array1[i];
                }
            }
        }
    }

}
