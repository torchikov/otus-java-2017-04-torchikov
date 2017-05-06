package ru.otus.torchikov;

import ru.otus.torchikov.exceptions.PackageNotFoundException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 * It finds classes in specified package.
 */
final class ClassFinder {

    private ClassFinder() {
    }

    /**
     * @return Classes which contain in the specified package
     */
    static Class<?>[] getClasses(String packageName) throws URISyntaxException, IOException {
        String fileSeparator = Matcher.quoteReplacement(File.separator);

        URL url = Optional.ofNullable(Thread.currentThread().getContextClassLoader().getResource(packageName.replaceAll("\\.", fileSeparator)))
                .orElseThrow(() -> new PackageNotFoundException("Package " + packageName + " not found"));
        URI root = url.toURI();

        Path path = Paths.get(root);
        List<Path> paths = Files.find(path, Integer.MAX_VALUE, (p, bfa) -> p.toString().endsWith(".class"))
                .collect(Collectors.toList());

        if (paths.isEmpty()) {
            return new Class[0];
        }

        List<Class<?>> list = paths.stream()
                .map(p -> {
                    String pathToFile = p.toString().replaceAll(fileSeparator, "\\.");
                    int start = pathToFile.indexOf(packageName); //Чтобы можно было искать от рутового пакета
                    return pathToFile.substring(start);
                })
                .map(f -> f.replace(".class", ""))
                .map(ClassFinder::getClassByName)
                .collect(Collectors.toList());

        return list.toArray(new Class[list.size()]);
    }

    private static Class<?> getClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
