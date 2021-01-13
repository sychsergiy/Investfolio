package investfolio.monitors;

import investfolio.AssetProfit;
import investfolio.Rate;
import investfolio.coinapi.CurrencyRateFetcher;
import investfolio.coinapi.FailedToFetchCurrencyRateException;

public class CurrencyProfitMonitor {
    private final CurrencyRateFetcher rateFetcher;
    private final ExpectedProfitAchievedHandler profitAchievedHandler;
    private final float checkAssetRateIntervalSeconds;

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
            float checkAssetRateIntervalSeconds
    ) {
        this.rateFetcher = rateFetcher;
        this.checkAssetRateIntervalSeconds = checkAssetRateIntervalSeconds;
        this.profitAchievedHandler = profitAchievedHandler;
    }

    public CurrencyProfitMonitor(
            CurrencyRateFetcher rateFetcher,
            ExpectedProfitAchievedHandler profitAchievedHandler,
            Rate initialAssetRate,
            AssetProfit expectedProfit,
            float checkAssetRateIntervalSeconds,
            String currency) {
        this.rateFetcher = rateFetcher;
        this.checkAssetRateIntervalSeconds = checkAssetRateIntervalSeconds;
        this.profitAchievedHandler = profitAchievedHandler;

        this.initialAssetRate = initialAssetRate;
        this.expectedProfit = expectedProfit;
        this.currency = currency;
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

    public void monitor() {
        var runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (checkRate()) {
                            System.out.println("event successfully handled");
                            break;
                        }
                    } catch (FailedToFetchCurrencyRateException e) {
                        e.printStackTrace();
                        break;
                    }
                    try {
                        Thread.sleep((long) checkAssetRateIntervalSeconds * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
    }
}
