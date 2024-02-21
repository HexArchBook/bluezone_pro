package io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.matchers;

import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.PayErrorException;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import java.util.Objects;

public class IsAPayErrorExceptionWith extends TypeSafeMatcher<Exception> {

    private final String errorCode;

    public IsAPayErrorExceptionWith(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    protected boolean matchesSafely(Exception exception) {
        if ( exception==null) {
            return false;
        }
        if ( ! (exception instanceof PayErrorException) ) {
            return false;
        }
        PayErrorException payErrorException = (PayErrorException) exception;
        return Objects.equals(this.errorCode,payErrorException.getErrorCode());
    }

    @Override
    public void describeTo(Description description) {
        String text = String.format("a PayErrorException with error code = %s",this.errorCode);
        description.appendText(text);
    }

    public static Matcher<Exception> aPayErrorExceptionWith(String errorCode) {
        return new IsAPayErrorExceptionWith(errorCode);
    }

}
