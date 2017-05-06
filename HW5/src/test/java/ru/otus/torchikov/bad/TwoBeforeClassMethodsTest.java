package ru.otus.torchikov.bad;

import ru.otus.torchikov.annotations.MyBeforeClass;
import ru.otus.torchikov.annotations.MyTest;

import java.util.Arrays;
import java.util.List;

import static ru.otus.torchikov.MyAssertions.*;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 */
public class TwoBeforeClassMethodsTest {
    List<Integer> integerList;

    @MyBeforeClass
    public void setUp() {
        integerList = Arrays.asList(1, 2, 3, 4);
    }

    @MyBeforeClass
    public void setUp1() {

    }

    @MyTest
    public void test() {
        assertNotNull(integerList);
        assertTrue(!integerList.isEmpty());
        assertFalse(integerList.size() > 0);
    }
}

