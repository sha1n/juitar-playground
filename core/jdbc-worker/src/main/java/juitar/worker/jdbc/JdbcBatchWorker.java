package juitar.worker.jdbc;

import juitar.worker.queue.Result;
import juitar.worker.queue.Work;
import juitar.worker.queue.Worker;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public class JdbcBatchWorker implements Worker {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBatchWorker(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Result doWork(Work work) {

        String[] payload = (String[]) work.getPayload();
        int[] ints = jdbcTemplate.batchUpdate(payload);

        Result result = new Result(work.getId());
        result.setResultData(ints);

        return result;
    }

}