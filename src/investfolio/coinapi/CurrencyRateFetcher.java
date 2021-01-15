package investfolio.coinapi;

import investfolio.Rate;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyRateFetcher implements investfolio.repository.CurrencyRateFetcher {
    private final CoinApiClient client;
    private static final String endpointURL = "exchangerate";

    public CurrencyRateFetcher(CoinApiClient client) {
        this.client = client;
    }

    private String buildQueryString(String currency) {
        return String.format("%s/%s/%s", endpointURL, currency, "USD");
    }

    public Rate fetch(String currency) throws FailedToFetchCurrencyRateException {
        try {
            var json = new JSONObject(client.fetchJSON(buildQueryString(currency)));
            try {
                return Rate.create(json.getFloat("rate"));
            } catch (JSONException e) {
                throw new UnexpectedCurrencyRateResponseDataException(json, e);
            }
        } catch (CoinApiRequestFailed e) {
            throw new FailedToFetchCurrencyRateException("Unable to fetch currency rate", e);
        }
    }

}
