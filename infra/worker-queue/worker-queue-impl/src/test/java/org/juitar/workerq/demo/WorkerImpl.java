package org.juitar.workerq.demo;

import org.juitar.workerq.Result;
import org.juitar.workerq.Work;
import org.juitar.workerq.Worker;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class WorkerImpl implements Worker {
    @Override
    public void doWork(Work work) {
        Result result = new Result(work.getId());
        result.setResultData("data-" + work.getId());
        work.getCompletionCallback().onSuccess(result);
    }
}
