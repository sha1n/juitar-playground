package juitar.worker.queue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class Work {

    private final String id;
    private final Object payload;
    private final ResultChannel resultChannel;

    public Work(final String id, final Object payload, final ResultChannel resultChannel) {
        this.id = id;
        this.payload = payload;
        this.resultChannel = resultChannel;
    }

    public final String getId() {
        return id;
    }

    public final Object getPayload() {
        return payload;
    }

    public final ResultChannel getResultChannel() {
        return resultChannel;
    }


}
