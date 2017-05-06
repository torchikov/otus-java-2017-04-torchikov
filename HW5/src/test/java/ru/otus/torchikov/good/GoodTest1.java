package ru.otus.torchikov.good;

import ru.otus.torchikov.annotations.MyAfter;
import ru.otus.torchikov.annotations.MyBefore;
import ru.otus.torchikov.annotations.MyTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.otus.torchikov.MyAssertions.*;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 */
public class GoodTest1 {

    private List<Integer> integerList;
    private List<String> stringList;

    @MyBefore
    public void setUp() {
        integerList = new ArrayList<>();
        stringList = Arrays.asList("a", "aa", "aaa");
    }

    @MyAfter
    public void tearDown() {
        integerList = null;
        stringList = null;
    }

    @MyTest
    public void test() {
        assertNotNull(integerList);
        assertTrue(integerList.isEmpty());
        assertFalse(integerList.size() > 0);
    }

    @MyTest
    public void test1() {
        assertNotNull(stringList);
        assertFalse(stringList.isEmpty());
        assertTrue(stringList.size() == 3);
    }
}
