package investfolio.fetchers;

public class FailedToFetchAssetRateException extends Exception {
    FailedToFetchAssetRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
