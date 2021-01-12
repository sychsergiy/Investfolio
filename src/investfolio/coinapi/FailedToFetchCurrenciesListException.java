package investfolio.coinapi;

public class FailedToFetchCurrenciesListException extends Exception {
    public FailedToFetchCurrenciesListException(String message, Throwable e) {
        super(message, e);
    }
}
