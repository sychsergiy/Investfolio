package investfolio.coinapi;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class CurrenciesListFetcher {
    private final CoinApiClient client;

    public CurrenciesListFetcher(CoinApiClient client) {
        this.client = client;
    }

    private Set<String> parseJson(String json) {
        var array = new JSONArray(json);
        return IntStream
                .range(0, array.length())
                .mapToObj(array::getJSONObject)
                .filter(item -> item.getInt("type_is_crypto") == 1)
                .map(item -> item.getString("asset_id"))
                .collect(Collectors.toSet());
    }

    public Set<String> fetch() throws FailedToFetchCurrenciesListException {
        try {
            var json = client.fetchJSON("assets");
            try {
                return parseJson(json);
            } catch (JSONException e) {
                throw new UnexpectedCurrenciesListResponseDataException(json, e);
            }
        } catch (CoinApiRequestFailed e) {
            throw new FailedToFetchCurrenciesListException("Unable to fetch currencies list", e);
        }
    }
}
