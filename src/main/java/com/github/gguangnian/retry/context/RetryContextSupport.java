package com.github.gguangnian.retry.context;

import com.github.gguangnian.retry.support.AttributeAccessorSupport;

/**
 * 实现接口{@link RetryContext}
 */
public class RetryContextSupport extends AttributeAccessorSupport implements RetryContext {

    private final RetryContext parentContext;

    private boolean terminate = false;

    private int retryCount = 0;

    public RetryContextSupport() {
        this(null);
    }

    public RetryContextSupport(RetryContext parentContext) {
        this.parentContext = parentContext;
    }

    @Override
    public RetryContext getParentContext() {
        return parentContext;
    }

    @Override
    public boolean isExhaustedOnly() {
        return terminate;
    }

    @Override
    public void setExhaustedOnly() {
        this.terminate = true;
    }

    @Override
    public int getRetryCount() {
        return retryCount;
    }

    /**
     * 增加重试次数
     */
    public void incrementRetryCount() {
        retryCount++;
    }
}
