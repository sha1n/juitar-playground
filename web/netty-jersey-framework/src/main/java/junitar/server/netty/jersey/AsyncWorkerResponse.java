package junitar.server.netty.jersey;

import com.sun.jersey.api.JResponse;
import org.juitar.workerq.*;

/**
 * @author sha1n
 * Date: 1/23/13
 */
public class AsyncWorkerResponse<E> extends JResponse {

    private WorkQueue workQueue;
    private Work work;

    AsyncWorkerResponse(AsyncWorkerResponseBuilder<E> b) {
        super(b);
    }

    final void setWork(Work work) {
        this.work = work;
    }

    final void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }

    final void submitWork(final AsyncCompletionCallback callback) {
        Work callbackWorkWrapper = new Work(work.getId(), work.getPayload(), new CompletionCallback() {
            @Override
            public void onSuccess(Result result) {
                work.getCompletionCallback().onSuccess(result);
                callback.onSuccess();
            }

            @Override
            public void onFailure(Result result, Exception e, CompletionStatus status) {
                work.getCompletionCallback().onFailure(result, e, status);
                callback.onFailure();
            }
        });

        workQueue.submit(callbackWorkWrapper);
    }

}