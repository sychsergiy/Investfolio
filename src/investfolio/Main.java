package investfolio;

import investfolio.coinapi.CoinApiClient;
import investfolio.coinapi.CurrencyRateFetcher;
import investfolio.monitors.BTCRateMonitor;
import investfolio.monitors.PrintHandler;

public class Main {

    public static void main(String[] args) throws Exception {
        String coinApiSecretKey = System.getenv("COIN_API_SECRET_KEY");


        var fetcher = new CurrencyRateFetcher(
                new CoinApiClient(coinApiSecretKey)
        );


        new BTCRateMonitor(
                fetcher, new PrintHandler(),
                Rate.create(40000), AssetProfit.createFromPercentageValue(-10), 100
        ).monitor();
    }
}


