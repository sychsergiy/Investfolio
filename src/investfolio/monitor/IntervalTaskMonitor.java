package investfolio.monitor;


public class IntervalTaskMonitor implements Monitor {
    private MonitorTask task;
    private final IntervalTaskExecutor executor;

    public IntervalTaskMonitor(IntervalTaskExecutor executor) {
        this.executor = executor;
    }

    public void setTask(MonitorTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        executor.execute(task);
    }

    @Override
    public void stop() {
        executor.stop();
    }
}
