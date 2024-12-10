package com.github.gguangnian.retry.policy;

import com.github.gguangnian.retry.executor.DefaultExecutor;
import com.github.gguangnian.retry.executor.Executor;
import com.github.gguangnian.retry.factory.SimpleRetryCallableFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
public class AlwaysRetryPolicyTest {

    private final Executor executor =
            new DefaultExecutor(new AlwaysRetryPolicy());

    @Test
    public void onSuccessRetry() {
        assertEquals(SimpleRetryCallableFactory.SUCCEED_RESULT,
                this.executor.execute(SimpleRetryCallableFactory.onSuccessGreater(3)));

    }
}
