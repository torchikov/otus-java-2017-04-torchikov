package ru.otus.torchikov;

import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import ru.otus.torchikov.bad.TwoAfterClassMethodsTest;
import ru.otus.torchikov.bad.TwoBeforeClassMethodsTest;
import ru.otus.torchikov.exceptions.UniqueMethodConstraintException;
import ru.otus.torchikov.goodwithinner.Test1;
import ru.otus.torchikov.goodwithinner.Test2;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 * Test suites for {@link MyTestRunner}
 */
public class MyTestRunnerTest {
    private MyTestRunner testRunner;

    @After
    public void clear() {
        testRunner = null;
    }

    @Test
    public void testWithOneClass() {
        testRunner = spy(new MyTestRunner(Test1.class));
        testRunner.runTests();
        verify(testRunner, times(2)).invokeTestMethod(ArgumentMatchers.any());
        //2 BeforeClass, 2 AfterClass and 2 tests
        verify(testRunner, times(6)).invokeMethod(any());
        verify(testRunner, times(2)).getClassInstance(any());
    }

    @Test
    public void testWithTwoClasses() {
        testRunner = spy(new MyTestRunner(Test1.class, Test2.class));
        testRunner.runTests();
        //4 BeforeClass, 4 AfterClass and 4 tests
        verify(testRunner, times(12)).invokeMethod(any());
        verify(testRunner, times(4)).getClassInstance(any());
    }

    @Test
    public void testWithPackage() {
        testRunner = spy(new MyTestRunner("ru.otus.torchikov.good"));
        testRunner.runTests();
        verify(testRunner, times(4)).invokeTestMethod(any());
        //2 BeforeClass, 2 Before, 2 AfterClass,2 After, 4 times Tests
        verify(testRunner, times(12)).invokeMethod(any());
        verify(testRunner, times(4)).getClassInstance(any());
    }

    @Test
    public void testWithRootPackage() {
        testRunner = spy(new MyTestRunner("ru.otus.torchikov.goodwithinner"));
        testRunner.runTests();
        verify(testRunner, times(8)).invokeTestMethod(any());
        //8 BeforeClass, 8 AfterClass, 8 Tests
        verify(testRunner, times(24)).invokeMethod(any());
        verify(testRunner, times(8)).getClassInstance(any());
    }


    @Test(expected = UniqueMethodConstraintException.class)
    public void testWithTwoBeforeClassMethods() {
        testRunner = spy(new MyTestRunner(TwoBeforeClassMethodsTest.class));
        testRunner.runTests();
    }

    @Test(expected = UniqueMethodConstraintException.class)
    public void testTwoAfterClassMethods() {
        testRunner = spy(new MyTestRunner(TwoAfterClassMethodsTest.class));
        testRunner.runTests();
    }


}
