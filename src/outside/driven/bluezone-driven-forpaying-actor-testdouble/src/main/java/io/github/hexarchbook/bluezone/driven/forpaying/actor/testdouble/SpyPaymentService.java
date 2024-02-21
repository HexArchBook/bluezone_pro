package io.github.hexarchbook.bluezone.driven.forpaying.actor.testdouble;

import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.ForPaying;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.PayError;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.PayErrorException;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.PayRequest;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivenActor;

import java.util.Random;
import java.util.UUID;

/**
 * Driven actor that implements "forpaying" port with a "spy" test-double.
 */
@DrivenActor(name="test-double")
public class SpyPaymentService implements ForPaying {

    private PayRequest payRequest;
    private String paymentId;
    private PayError payError;
    private int errorPercentage;

    public SpyPaymentService() {
        this.payRequest = null;
        this.paymentId = "";
        this.payError = null;
        setErrorPercentage(0);
    }

    @Override
    public String pay(PayRequest payRequest) throws PayErrorException {
        this.payRequest = payRequest;
        if ( anErrorShouldOccur() ) {
            this.paymentId = "";
            this.payError = PayError.random();
            throw new PayErrorException(this.payError);
        }
        this.payError = null;
        this.paymentId = UUID.randomUUID().toString();
        return this.paymentId;
    }

    @Override
    public PayRequest getPayRequest() {
        return this.payRequest;
    }

    @Override
    public String getPayResponse() {
        return this.paymentId;
    }

    @Override
    public void setErrorPercentage(int errorPercentage) {
        this.errorPercentage = validatePercent(errorPercentage);
    }

    @Override
    public String getErrorCode() {
        if ( this.payError==null ) {
            return "";
        }
        return this.payError.getCode();
    }

    private boolean anErrorShouldOccur() {
        int numberBetween1And100 = (new Random()).nextInt(100) + 1;
        if ( numberBetween1And100 <= this.errorPercentage ) {
            return true;
        }
        return false;
    }

    private int validatePercent(int percent) {
        if ( percent < 0 ) {
            return  0;
        }
        if ( percent > 100 ) {
            return  100;
        }
        return percent;
    }

}
