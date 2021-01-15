package investfolio;

import investfolio.coinapi.CoinApiClient;
import investfolio.coinapi.CurrencyRateFetcher;
import investfolio.monitor.CurrencyProfitCheckTask;
import investfolio.monitor.IntervalTaskExecutor;
import investfolio.monitor.IntervalTaskMonitor;

public class Main {
    public static void main(String[] args) throws Exception {
        String coinApiSecretKey = System.getenv("COIN_API_SECRET_KEY");
        var fetcher = new CurrencyRateFetcher(
                new CoinApiClient(coinApiSecretKey)
        );

        var task = new CurrencyProfitCheckTask(fetcher);
        task.setCurrency("BTC");
        task.setExpectedProfit(AssetProfit.createFromPercentageValue(-1));
        task.setInitialRate(Rate.create(40000));


        var monitor = new IntervalTaskMonitor(new IntervalTaskExecutor(30));
        monitor.setTask(task);
        monitor.run();
    }
}


