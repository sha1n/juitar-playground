package juitar.worker.queue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class ResultChannelImpl implements ResultChannel {
    @Override
    public void send(Result result) {
        System.out.println(result);
    }
}
