package junitar.server.netty.jersey;

import com.sun.jersey.api.JResponse;
import org.juitar.workerq.Work;
import org.juitar.workerq.WorkQueue;

/**
 * @author sha1n
 * Date: 1/24/13
 */
public class AsyncWorkerResponseBuilder<E> extends JResponse.AJResponseBuilder<E, AsyncWorkerResponseBuilder<E>> {

    private final WorkQueue workQueue;
    private final Work work;

    public AsyncWorkerResponseBuilder(final WorkQueue workQueue, final Work work) {
        this.workQueue = workQueue;
        this.work = work;
    }

    public AsyncWorkerResponse<E> build() {
        AsyncWorkerResponse<E> workerResponse = new AsyncWorkerResponse<>(this);
        workerResponse.setWork(work);
        workerResponse.setWorkQueue(workQueue);
        reset();

        return workerResponse;
    }

}
