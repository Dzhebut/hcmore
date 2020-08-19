package cn.homecredit.bbc.hcmore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

class HcmoreApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void justPractise() throws InterruptedException, ExecutionException {
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                System.out.println("callable task");
                return "task";
            }
        };
        BlockingQueue<Runnable>queue = new ArrayBlockingQueue<>(10);
        ExecutorService es = new ThreadPoolExecutor(5, 10, 3, TimeUnit.SECONDS, queue);
        Future<String> future = es.submit(task);
        System.out.println(future.get());

    }

}
