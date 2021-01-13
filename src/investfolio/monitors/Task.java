package investfolio.monitors;

public interface Task {
    /**
     * @return true if task should be stopped
     */
    boolean execute() throws Exception;
}
