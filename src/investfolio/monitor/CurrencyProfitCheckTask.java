package investfolio.monitor;

import investfolio.AssetProfit;
import investfolio.Rate;
import investfolio.coinapi.CurrencyRateFetcher;

public class CurrencyProfitCheckTask implements MonitorTask {
    private String currency;
    private final CurrencyRateFetcher rateFetcher;
    private AssetProfit expectedProfit;
    private Rate initialRate;

    public CurrencyProfitCheckTask(CurrencyRateFetcher rateFetcher) {
        this.rateFetcher = rateFetcher;
    }

    public void setExpectedProfit(AssetProfit expectedProfit) {
        this.expectedProfit = expectedProfit;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setInitialRate(Rate initialRate) {
        this.initialRate = initialRate;
    }

    private void notifyCompleted(AssetProfit currentProfit) {
        System.out.printf(
                "Expected profit for %s%% achieved, current profit: %s%%\n",
                expectedProfit.getPercentageValue(), currentProfit.getPercentageValue()
        );
    }

    private boolean sendNotificationIfExpectedProfitAchieved() {
        var rate = rateFetcher.fetch(currency);
        var currentProfit = rate.calcProfit(initialRate);
        if (currentProfit.isProfitAchieved(expectedProfit)) {
            notifyCompleted(currentProfit);
            return true;
        } else {
            System.out.printf(
                    "Current currency rate: %s; Current currency profit: %.2f2%%\n",
                    rate.getValue(), currentProfit.getPercentageValue()
            );
            return false;
        }
    }

    @Override
    public boolean execute() {
        return this.sendNotificationIfExpectedProfitAchieved();
    }
}
