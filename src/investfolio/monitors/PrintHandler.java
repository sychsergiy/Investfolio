package investfolio.monitors;

import investfolio.AssetProfit;

public class PrintHandler implements ExpectedProfitAchievedHandler {
    public void handle(AssetProfit profit) {
        System.out.println(profit.getPercentageValue());
    }
}
