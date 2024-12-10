package com.github.gguangnian.retry.policy;

import com.github.gguangnian.retry.context.RetryContext;

/**
 * 最大重试次数
 */
public class MaxAttemptsRetryPolicy extends RetryPolicyAdapter{

    /**
     * 默认最大重试次数: 3
     */
    private static final int DEFAULT_MAX_ATTEMPTS = 3;
    /**
     * 最大重试次数
     */
    private final int maxAttempts;

    /**
     * 最大重试次数
     * @param maxAttempts 大于0
     */
    public MaxAttemptsRetryPolicy(int maxAttempts) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("Max attempts must be greater than 0");
        }
        this.maxAttempts = maxAttempts;
    }

    /**
     * 获取最大重试次数
     * @return 最大重试次数
     */
    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public boolean allowRetry(RetryContext context) {
        return context.getRetryCount() < getMaxAttempts();
    }

}
