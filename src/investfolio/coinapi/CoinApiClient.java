package investfolio.coinapi;

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


    public String fetchJSON(String url) throws CoinApiRequestFailed, UnexpectedCurrencyRateResponseException {
        var request = HttpRequest.newBuilder(
                URI.create(String.format("%s/%s", apiURL, url)))
                .header("accept", "application/json")
                .header("X-CoinAPI-Key", this.secretKey)
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new UnexpectedCurrencyRateResponseException(response);
            }
            return response.body();

        } catch (IOException | InterruptedException e) {
            var error = new CoinApiRequestFailed();
            error.initCause(e);
            throw error;
        }
    }
}
