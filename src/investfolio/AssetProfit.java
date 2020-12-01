package investfolio;

public class AssetProfit implements Comparable<AssetProfit> {
    private final float value;

    private AssetProfit(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public float getPercentageValue() {
        return value * 100;
    }

    public static AssetProfit createFromValue(float value) {
        return new AssetProfit(value);
    }

    public static AssetProfit createFromPercentageValue(float value) {
        return new AssetProfit(value / 100);
    }

    public boolean isPositive() {
        return this.value >= 0;
    }

    public boolean isNegative() {
        return this.value < 0;
    }

    public boolean isProfitAchieved(AssetProfit expectedProfit) {
        if (expectedProfit.isPositive() && this.isGreaterThan(expectedProfit)) {
            return true;
        } else if (expectedProfit.isNegative() && this.isLessThan(expectedProfit)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(AssetProfit o) {
        return Float.compare(this.value, o.value);
    }

    public boolean isGreaterThan(AssetProfit o) {
        return this.compareTo(o) > 0;
    }

    public boolean isLessThan(AssetProfit o) {
        return this.compareTo(o) < 0;
    }

    public boolean isEqualTo(AssetProfit o) {
        return this.compareTo(o) == 0;
    }
}
