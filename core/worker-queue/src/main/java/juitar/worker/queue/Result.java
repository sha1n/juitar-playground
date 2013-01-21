package juitar.worker.queue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class Result {

    private final String workId;
    private Object resultData;

    public Result(String workId) {
        this.workId = workId;
    }

    public final String getWorkId() {
        return workId;
    }

    public final Object getResultData() {
        return resultData;
    }

    public final void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    @Override
    public String toString() {
        return "Result{" +
                "workId='" + workId + '\'' +
                ", resultData=" + resultData +
                '}';
    }

}
