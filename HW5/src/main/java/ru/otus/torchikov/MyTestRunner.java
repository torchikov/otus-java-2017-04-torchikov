package ru.otus.torchikov;

import ru.otus.torchikov.annotations.*;
import ru.otus.torchikov.exceptions.MyAssertException;
import ru.otus.torchikov.exceptions.UniqueMethodConstraintException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 * Core class for processing tests
 */
// подавляю WeakerAccess т.к. теоретически если это тест движок, то можно собрать его в jar
// и использовать в другом проекте, тогда конструкторы и метод runTests должны быть public
// OptionalUsedAsFieldOrParameterType подавляю, т.к. использую Optional как поле обьекта, сериализация мне не нужна
@SuppressWarnings({"WeakerAccess", "OptionalUsedAsFieldOrParameterType"})
public class MyTestRunner {
    private Class<?>[] testClasses;
    private String packageWithTests;
    private Optional<Method> beforeClassMethod;
    private Optional<Method> afterClassMethod;
    private Optional<Method> afterMethod;
    private Optional<Method> beforeMethod;
    private Object target;
    private String testResultMessage;


    public MyTestRunner(Class<?>... testClasses) {
        this.testClasses = testClasses;
    }

    public MyTestRunner(String packageWithTests) {
        this.packageWithTests = packageWithTests;
    }


    public void runTests() {
        if (Objects.nonNull(testClasses) && testClasses.length != 0) {
            runWithClasses();
        } else if (Objects.nonNull(packageWithTests) && packageWithTests.length() != 0) {
            runWithPackage();
        } else {
            throw new IllegalArgumentException("You have to set classes or package with tests before run tests! Use one of MyTestRunner constructor!");
        }
    }

    private void runWithClasses() {
        Map<Class<?>, List<Method>> classToMethods = new HashMap<>();
        for (Class<?> clazz : testClasses) {
            List<Method> methods = findMethodsWithAnnotation(clazz.getMethods(), MyTest.class);
            if (methods.isEmpty()) {
                continue;
            }
            classToMethods.put(clazz, methods);
        }
        classToMethods.forEach(this::invokeAllMethods);
    }

    private void runWithPackage() {
        try {
            this.testClasses = ClassFinder.getClasses(this.packageWithTests);
            runWithClasses();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void invokeAllMethods(Class<?> clazz, List<Method> methods) {
        System.out.println("======= Run test in class " + clazz.getName() + " ======");
        target = getClassInstance(clazz);
        registerBeforeAndAfterMethods(clazz);
        beforeClassMethod.ifPresent(this::invokeMethod);
        methods.forEach(this::invokeTestMethod);
        afterClassMethod.ifPresent(this::invokeMethod);
    }

    private Object getClassInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            RuntimeException ex = new RuntimeException("Error while initialize class " + clazz);
            ex.addSuppressed(e);
            throw ex;
        }
    }

    void invokeTestMethod(Method method) {
        System.out.println("Method " + method.getName() + " result: ");
        this.testResultMessage = "Success";
        beforeMethod.ifPresent(this::invokeMethod);
        invokeMethod(method);
        afterMethod.ifPresent(this::invokeMethod);
        System.out.println(this.testResultMessage);

    }

    void invokeMethod(Method method) {
        boolean isAccessible = method.isAccessible();
        try {
            method.setAccessible(true);
            method.invoke(this.target);
        } catch (IllegalAccessException | InvocationTargetException e) {
            if (e.getCause() instanceof MyAssertException) {
                this.testResultMessage = "";
                e.getCause().printStackTrace();
            } else {
                e.printStackTrace();
            }
        } finally {
            if (!isAccessible) {
                method.setAccessible(false);
            }
        }
    }

    private void registerBeforeAndAfterMethods(Class<?> clazz) {
        clearBeforeAndAfterMethods();
        this.beforeClassMethod = getOneAnnotatedMethodOrThrowException(MyBeforeClass.class, clazz, "Before class method must be exactly one!");
        this.afterClassMethod = getOneAnnotatedMethodOrThrowException(MyAfterClass.class, clazz, "After class method must be exactly one!");
        this.beforeMethod = getOneAnnotatedMethodOrThrowException(MyBefore.class, clazz, "Before method must be exactly one!");
        this.afterMethod = getOneAnnotatedMethodOrThrowException(MyAfter.class, clazz, "After method must be exactly one!");
    }

    private Optional<Method> getOneAnnotatedMethodOrThrowException(Class<? extends Annotation> annotation, Class<?> clazz, String exceptionMessage) {
        List<Method> methods = findMethodsWithAnnotation(clazz.getMethods(), annotation);
        if (methods.size() > 1) {
            throw new UniqueMethodConstraintException(exceptionMessage);
        }
        if (methods.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(methods.get(0));
    }

    private List<Method> findMethodsWithAnnotation(Method[] methods, Class<? extends Annotation> annotation) {
        return Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(annotation))
                .filter(m -> m.getReturnType().getName().equals("void"))
                .filter(m -> m.getModifiers() == Modifier.PUBLIC)
                .collect(Collectors.toList());
    }

    private void clearBeforeAndAfterMethods() {
        this.beforeClassMethod = Optional.empty();
        this.afterClassMethod = Optional.empty();
        this.beforeMethod = Optional.empty();
        this.afterMethod = Optional.empty();
    }

}
