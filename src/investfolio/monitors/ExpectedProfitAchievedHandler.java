package investfolio.monitors;

import investfolio.AssetProfit;

public interface ExpectedProfitAchievedHandler {
    void handle(AssetProfit profit);
}
