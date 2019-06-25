package demo.guavaretry;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class RetryDemo {

    public static void main(String[] args) {
        final long start = System.currentTimeMillis();
        Callable<Boolean> callable = new Callable<Boolean>() {
            public Boolean call() throws Exception {
                System.out.println("retry" + " " + (System.currentTimeMillis() - start));
                return null; // do something useful here
            }
        };

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.<Boolean>isNull())
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withWaitStrategy(WaitStrategies.fibonacciWait(100, 2, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(10))
                .build();
        try {
            retryer.call(callable);
        } catch (RetryException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
