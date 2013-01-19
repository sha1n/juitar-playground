package juitar.worker.queue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class Work {

    private final String id;
    private final String payload;
    private final ResultChannel resultChannel;

    public Work(final String id, final String payload, final ResultChannel resultChannel) {
        this.id = id;
        this.payload = payload;
        this.resultChannel = resultChannel;
    }

    public final String getId() {
        return id;
    }

    public final String getPayload() {
        return payload;
    }

    public final ResultChannel getResultChannel() {
        return resultChannel;
    }


}
