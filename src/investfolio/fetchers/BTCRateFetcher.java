package investfolio.fetchers;

import investfolio.Rate;
import org.json.JSONException;
import org.json.JSONObject;

public class BTCRateFetcher implements AssetRateFetcher {
    String currency = "BTC";
    CoinApiClient client;

    public BTCRateFetcher(CoinApiClient client) {
        this.client = client;
    }

    public Rate fetch() throws FailedToFetchAssetRateException {
        try {
            var response = this.client.fetchCurrencyRate(this.currency);
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
            throw new FailedToFetchAssetRateException("Unable to fetch currency rate", e);
        }
    }
}
