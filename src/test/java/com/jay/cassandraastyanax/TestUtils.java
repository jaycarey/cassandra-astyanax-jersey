package com.jay.cassandraastyanax;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Pattern;

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
