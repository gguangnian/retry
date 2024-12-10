package com.github.gguangnian.retry.policy;

import com.github.gguangnian.retry.context.RetryContext;

/**
 * 重试策略适配器
 */
public abstract class RetryPolicyAdapter implements RetryPolicy{

    @Override
    public RetryContext beforeInitialization(RetryContext context) {
        return context;
    }

    @Override
    public boolean allowRetry(RetryContext context) {
        return false;
    }

    @Override
    public void processThrowing(Exception exception, RetryContext context) {

    }

    @Override
    public void beforeDestruction(RetryContext context) {

    }
}
