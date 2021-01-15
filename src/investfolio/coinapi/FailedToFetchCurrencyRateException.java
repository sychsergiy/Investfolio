package investfolio.coinapi;

public class FailedToFetchCurrencyRateException extends RuntimeException {
    public FailedToFetchCurrencyRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
