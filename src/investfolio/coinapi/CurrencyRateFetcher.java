package investfolio.coinapi;

import investfolio.Rate;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyRateFetcher {
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
            var response = client.fetch(buildQueryString(currency));
            if (response.statusCode() != 200) {
                throw new UnexpectedCurrencyRateResponse(response);
            }
            JSONObject json = new JSONObject(response.body());
            try {
                return Rate.create(json.getFloat("rate"));
            } catch (JSONException e) {
                throw new UnexpectedCurrencyRateResponse(response, e);
            }
        } catch (CoinApiRequestFailed e) {
            throw new FailedToFetchCurrencyRateException("Unable to fetch currency rate", e);
        }
    }

}
