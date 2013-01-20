package juitar.worker.jdbc;

import juitar.worker.queue.Result;
import juitar.worker.queue.Work;
import juitar.worker.queue.Worker;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

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

        List<String> payload = (List<String>) work.getPayload();
        int[] ints = jdbcTemplate.batchUpdate(payload.toArray(new String[payload.size()]));

        Result result = new Result(work.getId());
        result.setResultData(ints);

        return result;
    }

}
