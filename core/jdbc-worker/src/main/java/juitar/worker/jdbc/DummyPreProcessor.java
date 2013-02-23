package juitar.worker.jdbc;

import org.juitar.workerq.Work;

/**
 * @author sha1n
 * Date: 2/23/13
 */
public class DummyPreProcessor implements WorkPreProcessor {
    @Override
    public Work process(Work work) {
        return work;
    }
}
