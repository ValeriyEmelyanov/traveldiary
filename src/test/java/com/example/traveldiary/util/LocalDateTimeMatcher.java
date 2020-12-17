package com.example.traveldiary.util;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A matcher for checking strings containing date-time values
 */
public class LocalDateTimeMatcher extends TypeSafeDiagnosingMatcher<String> {
    private LocalDateTime before;
    private LocalDateTime after;

    public LocalDateTimeMatcher(LocalDateTime before, LocalDateTime after) {
        this.before = before;
        this.after = after;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("the value is between 'befor' and 'after' values (including borders)");
    }

    @Override
    protected boolean matchesSafely(String stringNow, Description description) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.parse(stringNow, formatter);

        before = before.withNano(0).withSecond(0);
        after = after.withNano(0).withSecond(0);

        description.appendText(String.format("'%s' is not between '%s' and '%s'",
                stringNow, before.toString(), after.toString()));

        return (now.isAfter(before) || now.equals(before))
                && (now.isAfter(after) || now.isEqual(after));
    }

    /**
     * Creates a matcher that matches if the examined string contains the local date-time
     * and the date-time is between the before value and the after value including borders
     * @param before the date-time before the examined value
     * @param after the date-time after the examined value
     * @return the matcher
     */
    public static LocalDateTimeMatcher betweenInclusive(LocalDateTime before, LocalDateTime after) {
        return new LocalDateTimeMatcher(before, after);
    }
}
