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
}