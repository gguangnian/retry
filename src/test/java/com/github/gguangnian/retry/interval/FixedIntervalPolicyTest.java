package com.github.gguangnian.retry.interval;

import com.github.gguangnian.retry.executor.DefaultExecutor;
import com.github.gguangnian.retry.executor.Executor;
import com.github.gguangnian.retry.factory.SimpleRetryCallableFactory;
import com.github.gguangnian.retry.policy.MaxAttemptsRetryPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
public class FixedIntervalPolicyTest {

    private final Executor executor =
            new DefaultExecutor(new MaxAttemptsRetryPolicy(3), new FixedIntervalPolicy());


    @Test
    public void testFixedInterval() {
        assertEquals(SimpleRetryCallableFactory.SUCCEED_RESULT,
                this.executor.execute(SimpleRetryCallableFactory.onSuccessGreater(2)));
    }
}
