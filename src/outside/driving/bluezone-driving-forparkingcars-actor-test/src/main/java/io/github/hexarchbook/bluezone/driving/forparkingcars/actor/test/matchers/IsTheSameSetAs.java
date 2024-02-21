package io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.matchers;

import io.github.hexarchbook.bluezone.lib.javautils.CollectionUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import java.util.Set;

public class IsTheSameSetAs extends TypeSafeMatcher<Set<?>> {

    private final Set<?> aSet;

    public IsTheSameSetAs(Set<?> aSet) {
        this.aSet = aSet;
    }

    @Override
    protected boolean matchesSafely(Set<?> anotherSet) {
        return CollectionUtils.areEqualSets(this.aSet,anotherSet);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("the same set as " + this.aSet);
    }

    public static Matcher<Set<?>> theSameSetAs(Set<?> aSet) {
        return new IsTheSameSetAs(aSet);
    }

}