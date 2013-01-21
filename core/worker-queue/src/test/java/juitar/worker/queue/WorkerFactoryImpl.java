package juitar.worker.queue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class WorkerFactoryImpl implements WorkerFactory {
    @Override
    public Worker createWorker() {
        return new WorkerImpl();
    }
}
