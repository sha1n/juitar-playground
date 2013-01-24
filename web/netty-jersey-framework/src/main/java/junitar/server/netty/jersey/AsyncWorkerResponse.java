package junitar.server.netty.jersey;

import com.sun.jersey.api.JResponse;
import juitar.worker.queue.Result;
import juitar.worker.queue.ResultChannel;
import juitar.worker.queue.Work;
import juitar.worker.queue.WorkQueue;

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
        Work callbackWorkWrapper = new Work(work.getId(), work.getPayload(), new ResultChannel() {
            @Override
            public void onSuccess(Result result) {
                work.getResultChannel().onSuccess(result);
                callback.onSuccess();
            }

            @Override
            public void onFailure(Result result, Exception e) {
                work.getResultChannel().onFailure(result, e);
                callback.onFailure();
            }
        });

        workQueue.submit(callbackWorkWrapper);
    }

}