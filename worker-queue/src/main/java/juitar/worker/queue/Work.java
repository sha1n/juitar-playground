package juitar.worker.queue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class Work {

    private final String id;
    private final String payload;
    private final ResultChannel resultChannel;

    public Work(String id, String payload, ResultChannel resultChannel) {
        this.id = id;
        this.payload = payload;
        this.resultChannel = resultChannel;
    }

    public String getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public ResultChannel getResultChannel() {
        return resultChannel;
    }


}
