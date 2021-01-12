package investfolio.fetchers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CoinApiClient {
    private final static String apiURL = "https://rest.coinapi.io/v1";
    private final String secretKey;
    HttpClient client = HttpClient.newHttpClient();

    public CoinApiClient(String secretKey) {
        this.secretKey = secretKey;
    }


    public HttpResponse<String> fetchCurrencyRate(String currency) throws CoinApiRequestFailed {
        var request = HttpRequest.newBuilder(
                URI.create(String.format("%s/exchangerate/%s/USD", apiURL, currency)))
                .header("accept", "application/json")
                .header("X-CoinAPI-Key", this.secretKey)
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            var error = new CoinApiRequestFailed();
            error.initCause(e);
            throw error;
        }
    }
}
