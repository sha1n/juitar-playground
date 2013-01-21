package juitar.worker.queue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public interface ResultChannel {

    void send(Result result);
}
