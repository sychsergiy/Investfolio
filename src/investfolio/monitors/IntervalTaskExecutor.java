package investfolio.monitors;

public class IntervalTaskExecutor {
    private final long checkAssetRateIntervalSeconds;

    public IntervalTaskExecutor(long checkAssetRateIntervalSeconds) {
        this.checkAssetRateIntervalSeconds = checkAssetRateIntervalSeconds;
    }

    public void run(Task task) {
        var runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (task.execute()) {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                    try {
                        Thread.sleep(checkAssetRateIntervalSeconds * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
    }
}
