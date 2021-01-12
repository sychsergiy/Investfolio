package investfolio.coinapi;

import java.net.http.HttpResponse;

public class UnexpectedCurrencyRateResponseException extends RuntimeException {
    private final HttpResponse<String> response;

    public UnexpectedCurrencyRateResponseException(HttpResponse<String> response, Throwable cause) {
        super(
                String.format(
                        "Received unexpected response: \nStatus code: %d\n Body: %s",
                        response.statusCode(), response.body()),
                cause
        );
        this.response = response;
    }

    public UnexpectedCurrencyRateResponseException(HttpResponse<String> response) {
        super(String.format(
                "Received unexpected response: \nStatus code: %d\n Body: %s", response.statusCode(), response.body()
        ));
        this.response = response;
    }

    public HttpResponse<String> getResponse() {
        return response;
    }
}
