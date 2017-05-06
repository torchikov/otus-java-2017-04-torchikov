package ru.otus.torchikov.goodwithinner;

import ru.otus.torchikov.annotations.MyAfterClass;
import ru.otus.torchikov.annotations.MyBeforeClass;
import ru.otus.torchikov.annotations.MyTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.otus.torchikov.MyAssertions.*;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 */
public class Test1 {

    private List<Integer> integerList;
    private List<String> stringList;

    @MyBeforeClass
    public void setUp() {
        integerList = new ArrayList<>();
        stringList = Arrays.asList("a", "aa", "aaa");
    }

    @MyAfterClass
    public void tearDown() {
        integerList = null;
        stringList = null;
    }

    @MyTest
    public void test() {
        assertNotNull(integerList);
        assertTrue(!integerList.isEmpty()); // Will fail
        assertFalse(integerList.size() > 0);
    }

    @MyTest
    public void test1() {
        assertNotNull(stringList);
        assertFalse(stringList.isEmpty());
        assertTrue(stringList.size() == 3);
    }
}
