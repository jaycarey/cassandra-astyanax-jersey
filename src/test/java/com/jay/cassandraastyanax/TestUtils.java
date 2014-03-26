package com.jay.cassandraastyanax;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.omg.DynamicAny._DynArrayStub;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

/**
 * @author jaycarey
 */
public class TestUtils {

    public static Matcher<String> matchesPattern(final String regex) {
        return new TypeSafeMatcher<String>() {

            private final Pattern pattern = Pattern.compile(regex);

            @Override
            public boolean matchesSafely(String string) {
                return pattern.matcher(string).matches();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Should match " + regex);
            }
        };
    }

    @Test
    public void instantiateAllExceptions() throws Exception {
        for (Class<?> aClass : findClassesInPackage("com/jay/cassandraastyanax")) {
            if (aClass.getName().endsWith("Exception")) {
                for (Constructor<?> constructor : aClass.getConstructors()) {
                    instantiate(constructor);
                }
            }

            for (Constructor<?> constructor : aClass.getConstructors()) {
                if (Modifier.isPrivate(constructor.getModifiers())) {
                    instantiate(constructor);
                }
            }

        }
    }

    private void instantiate(Constructor<?> constructor) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        List<Object> args = new ArrayList<>();
        for (Class<?> parameterClass : constructor.getParameterTypes()) {
            args.add(parameterClass.newInstance());
        }
        constructor.newInstance(args.toArray());
    }

    private List<Class<?>> findClassesInPackage(String basePackage) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        Enumeration<URL> enumeration = ClassLoader.getSystemClassLoader().getResources(basePackage);
        while (enumeration.hasMoreElements()) {
            for (String file : files(enumeration.nextElement().getPath())) {
                String name = file.substring(file.indexOf(basePackage)).replace("/", ".");
                classes.add(Class.forName(name.substring(0, name.indexOf(".class"))));
            }
        }
        return classes;
    }

    private List<String> files(String path) {
        File[] children = new File(path).listFiles();
        if (children != null) {
            return getChildren(children);
        } else {
            return asList(path);
        }
    }

    private List<String> getChildren(File[] children) {
        List<String> files = new ArrayList<>();
        for (File child : children) {
            files.addAll(files(child.getAbsolutePath()));
        }
        return files;
    }
}