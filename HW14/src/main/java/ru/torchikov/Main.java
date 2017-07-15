package ru.torchikov;

import java.util.Arrays;

/**
 * Created by sergei on 15.07.17.
 */
public class Main {
    public static void main(String[] args) {
        int[] array = {5, 12, 78, 4, 6, 32, 10, 6, 15, 99};
        int[] result = Arrays.stream(array)
                .parallel()
                .sorted()
                .toArray();
        System.out.println(Arrays.toString(result));
    }
}
