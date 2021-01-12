package investfolio.coinapi;

public class UnexpectedCurrenciesListResponseDataException extends RuntimeException {
    UnexpectedCurrenciesListResponseDataException(String message, Throwable e) {
        super(message, e);
    }
}
