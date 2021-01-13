package investfolio;

import investfolio.coinapi.CoinApiClient;
import investfolio.coinapi.CurrencyRateFetcher;
import investfolio.monitors.CurrencyProfitMonitor;
import investfolio.monitors.CurrencyProfitMonitorBuilder;
import investfolio.monitors.PrintHandler;

public class Main {

    public static void main(String[] args) throws Exception {
        String coinApiSecretKey = System.getenv("COIN_API_SECRET_KEY");


        var fetcher = new CurrencyRateFetcher(
                new CoinApiClient(coinApiSecretKey)
        );


        new CurrencyProfitMonitorBuilder(fetcher, new PrintHandler(), 100)
                .setCurrency("BTC")
                .setInitialRate(Rate.create(40000))
                .setExpectedProfit(AssetProfit.createFromPercentageValue(-10))
                .build()
                .monitor();
    }
}


