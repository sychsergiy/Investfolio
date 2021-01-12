package investfolio.monitors;

import investfolio.AssetProfit;
import investfolio.Rate;
import investfolio.coinapi.CurrencyRateFetcher;
import investfolio.coinapi.FailedToFetchCurrencyRateException;

public class BTCRateMonitor {
    private final Rate initialAssetRate;
    private final float checkAssetRateIntervalSeconds;
    private final AssetProfit expectedProfit;

    private final ExpectedProfitAchievedHandler profitAchievedHandler;
    private final CurrencyRateFetcher rateFetcher;

    public BTCRateMonitor(
            CurrencyRateFetcher rateFetcher,
            ExpectedProfitAchievedHandler profitAchievedHandler,
            Rate initialAssetRate,
            AssetProfit expectedProfit,
            float checkAssetRateIntervalSeconds
    ) {
        this.rateFetcher = rateFetcher;
        this.profitAchievedHandler = profitAchievedHandler;
        this.initialAssetRate = initialAssetRate;
        this.checkAssetRateIntervalSeconds = checkAssetRateIntervalSeconds;
        this.expectedProfit = expectedProfit;
    }

    private boolean checkRate() throws FailedToFetchCurrencyRateException {
        var rate = this.rateFetcher.fetch("BTC");
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
