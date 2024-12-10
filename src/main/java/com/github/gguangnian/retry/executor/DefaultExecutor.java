package com.github.gguangnian.retry.executor;

import com.github.gguangnian.retry.interval.IntervalPolicy;
import com.github.gguangnian.retry.policy.RetryPolicy;

import java.util.Objects;

/**
 *
 */
public class DefaultExecutor extends AbstractConfigurableExecutor {

    public DefaultExecutor() {
    }

    public DefaultExecutor(RetryPolicy retryPolicy) {
        Objects.requireNonNull(retryPolicy, "Retry policy must not be null");
        setRetryPolicy(retryPolicy);
    }

    public DefaultExecutor(RetryPolicy retryPolicy, IntervalPolicy intervalPolicy) {
        Objects.requireNonNull(retryPolicy, "Retry policy must not be null");
        Objects.requireNonNull(intervalPolicy, "Interval policy must not be null");
        setRetryPolicy(retryPolicy);
        setIntervalPolicy(intervalPolicy);
    }
}
