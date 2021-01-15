package investfolio.monitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IntervalTaskExecutor {
    private long periodInSeconds;
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public IntervalTaskExecutor(long periodInSeconds) {
        this.periodInSeconds = periodInSeconds;
    }

    private void createNewExecutorIfNeeded() {
        if (executor.isShutdown()) {
            executor = Executors.newSingleThreadScheduledExecutor();
        }
    }

    public void setPeriodInSeconds(long periodInSeconds) {
        this.periodInSeconds = periodInSeconds;
    }

    public void execute(MonitorTask task) {
        createNewExecutorIfNeeded();
        executor.scheduleAtFixedRate(task::execute, 0, periodInSeconds, TimeUnit.SECONDS);
    }

    public void stop() {
        executor.shutdown();
    }
}