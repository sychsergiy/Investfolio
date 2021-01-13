package investfolio.monitors;

import investfolio.AssetProfit;
import investfolio.Rate;
import investfolio.coinapi.CurrencyRateFetcher;

public class CurrencyProfitMonitorBuilder {
    private final CurrencyProfitMonitor monitor;

    public CurrencyProfitMonitorBuilder(
            CurrencyRateFetcher rateFetcher,
            ExpectedProfitAchievedHandler profitAchievedHandler,
            IntervalTaskExecutor executor
    ) {
        this.monitor = new CurrencyProfitMonitor(rateFetcher, profitAchievedHandler, executor);
    }

    public CurrencyProfitMonitorBuilder setExpectedProfit(AssetProfit expectedProfit) {
        this.monitor.setExpectedProfit(expectedProfit);
        return this;
    }

    public CurrencyProfitMonitorBuilder setInitialRate(Rate initialRate) {
        this.monitor.setInitialAssetRate(initialRate);
        return this;
    }

    public CurrencyProfitMonitorBuilder setCurrency(String currency) {
        this.monitor.setCurrency(currency);
        return this;
    }

    public CurrencyProfitMonitor build() {
        return this.monitor;
    }
}
