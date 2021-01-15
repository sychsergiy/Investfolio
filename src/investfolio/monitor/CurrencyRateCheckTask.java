package investfolio.monitor;

import investfolio.Rate;
import investfolio.repository.CurrencyRateFetcher;

public class CurrencyRateCheckTask implements MonitorTask {
    private final CurrencyRateFetcher rateFetcher;
    private final Rate expectedRate;
    private final String currency;

    public CurrencyRateCheckTask(CurrencyRateFetcher rateFetcher, Rate expectedRate, String currency) {
        this.rateFetcher = rateFetcher;
        this.expectedRate = expectedRate;
        this.currency = currency;
    }

    private boolean isCurrencyRateAchieved() {
        var rate = this.rateFetcher.fetch(currency);
        System.out.printf("Current rate for '%s' is: %.1f\n", currency, rate.getValue());
        return rate.isRateAchieved(expectedRate);
    }

    private boolean sendNotificationIfExpectedCurrencyRateAchieved() {
        if (isCurrencyRateAchieved()) {
            System.out.printf("Currency '%s' has achieved rate: %.1f\n", currency, expectedRate.getValue());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean execute() {
        return sendNotificationIfExpectedCurrencyRateAchieved();
    }
}
