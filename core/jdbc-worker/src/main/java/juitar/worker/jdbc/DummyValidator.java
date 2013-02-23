package juitar.worker.jdbc;

import org.juitar.workerq.Work;

/**
 * @author sha1n
 * Date: 2/23/13
 */
public class DummyValidator implements WorkValidator {
    @Override
    public void validate(Work work) {
        if (work.getPayload() == null
                ||
                (work.getPayload().getClass() != String[].class
                        &&
                        work.getPayload().getClass() != String.class)) {
            throw new UnsupportedOperationException("Work payload must be of type String");
        }
    }
}
