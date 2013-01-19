package juitar.worker.queue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class WorkerImpl implements Worker {
    @Override
    public Result doWork(Work work) {
        Result result = new Result(work.getId());
        result.setResultData("data-" + work.getId());

        return result;
    }
}
