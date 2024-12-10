package com.github.gguangnian.retry.interval;

import com.github.gguangnian.retry.context.RetryContext;

/**
 * 不做任何事
 */
public class NoIntervalPolicy implements IntervalPolicy {

    @Override
    public IntervalContext create(RetryContext context) {
        return new NoIntervalContext();
    }

    @Override
    public void interval(IntervalContext context) {
    }

    private static class NoIntervalContext implements IntervalContext {
    }
}
