package investfolio.monitors;

import investfolio.AssetProfit;
import investfolio.Rate;
import investfolio.coinapi.CurrencyRateFetcher;
import investfolio.coinapi.FailedToFetchCurrencyRateException;

public class CurrencyProfitMonitor {
    private final CurrencyRateFetcher rateFetcher;
    private final ExpectedProfitAchievedHandler profitAchievedHandler;
    private final IntervalTaskExecutor executor;

    private AssetProfit expectedProfit;
    private Rate initialAssetRate;

    public AssetProfit getExpectedProfit() {
        return expectedProfit;
    }

    public void setExpectedProfit(AssetProfit expectedProfit) {
        this.expectedProfit = expectedProfit;
    }

    public Rate getInitialAssetRate() {
        return initialAssetRate;
    }

    public void setInitialAssetRate(Rate initialAssetRate) {
        this.initialAssetRate = initialAssetRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private String currency;

    public CurrencyProfitMonitor(
            CurrencyRateFetcher rateFetcher,
            ExpectedProfitAchievedHandler profitAchievedHandler,
            IntervalTaskExecutor executor) {
        this.rateFetcher = rateFetcher;
        this.profitAchievedHandler = profitAchievedHandler;
        this.executor = executor;
    }

    private boolean checkRate() throws FailedToFetchCurrencyRateException {
        var rate = this.rateFetcher.fetch(this.currency);
        var profit = rate.calcProfit(this.initialAssetRate);
        if (profit.isProfitAchieved(this.expectedProfit)) {
            this.profitAchievedHandler.handle(profit);
            return true;
        }
        return false;
    }

    public void run() {
        executor.run(this::checkRate);
    }
}
