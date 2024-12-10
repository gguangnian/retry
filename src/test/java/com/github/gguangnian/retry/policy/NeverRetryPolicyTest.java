package com.github.gguangnian.retry.policy;

import com.github.gguangnian.retry.exception.ExhaustedRetryException;
import com.github.gguangnian.retry.executor.DefaultExecutor;
import com.github.gguangnian.retry.executor.Executor;
import com.github.gguangnian.retry.factory.SimpleRetryCallableFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class NeverRetryPolicyTest {

    private final Executor executor =
            new DefaultExecutor(new NeverRetryPolicy());


    @Test
    public void onSuccessRetry() {
        assertEquals(SimpleRetryCallableFactory.SUCCEED_RESULT,
                this.executor.execute(SimpleRetryCallableFactory.onSuccessFast()));

    }

    @Test
    public void onFailRetry() {
        assertThrowsExactly(ExhaustedRetryException.class,
                () -> this.executor.execute(SimpleRetryCallableFactory.onFailFast()));
    }


}