package investfolio.coinapi;

public class FailedToFetchCurrencyRateException extends Exception {
    public FailedToFetchCurrencyRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
