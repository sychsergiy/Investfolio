package investfolio.coinapi;

import org.json.JSONObject;

public class UnexpectedCurrencyRateResponseDataException extends RuntimeException {
    public UnexpectedCurrencyRateResponseDataException(JSONObject json, Throwable cause) {
        super(json.toString(), cause);
    }

    public UnexpectedCurrencyRateResponseDataException(JSONObject json) {
        super(json.toString());
    }
}
