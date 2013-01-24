package juitar.worker.jdbc;

import juitar.worker.queue.Result;
import juitar.worker.queue.ResultChannel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author sha1n
 * Date: 1/20/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jdbc-worker-context.xml", "classpath:test-context.xml"})
public class JdbcBatchServiceTest {

    @Autowired
    JdbcBatchService service;

    @Test
    public void testBasic() throws InterruptedException {
        final AtomicReference<String> value = new AtomicReference<>();
        final Object lock = new Object();

        for (int i = 0; i < 10; i++) {
            service.executeUpdate("INSERT INTO TEST VALUES (1, 'str')", new ResultChannel() {
                @Override
                public void onSuccess(Result result) {
                    value.set(result.getWorkId());
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }

                @Override
                public void onFailure(Result result, Exception e) {
                    e.printStackTrace();
                    synchronized (lock) {
                        lock.notifyAll();
                    }

                }
            });
        }

        synchronized (lock) {
            lock.wait(3000);
        }

        Assert.assertNotNull(value.get());
    }
}
