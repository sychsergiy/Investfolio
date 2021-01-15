package investfolio;

public class Rate {
    private final float value; // USD

    private Rate(float value) {
        this.value = value;
    }

    public static Rate create(float value) {
        return new Rate(value);
    }

    public boolean isRateAchieved(Rate expectedRate) {
        return this.value >= expectedRate.value;
    }

    public AssetProfit calcProfit(Rate initialRate) {
        return AssetProfit.createFromValue(
                (this.value - initialRate.getValue()) / initialRate.getValue()
        );
    }

    public float getValue() {
        return value;
    }
}
