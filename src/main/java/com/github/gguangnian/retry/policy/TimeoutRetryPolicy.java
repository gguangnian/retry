package com.github.gguangnian.retry.policy;

import com.github.gguangnian.retry.context.RetryContext;
import com.github.gguangnian.retry.context.RetryContextSupport;

import java.time.Duration;
import java.util.Objects;

/**
 * 最大累积执行时间策略
 */
public class TimeoutRetryPolicy extends RetryPolicyAdapter{

    /**
     * 默认间隔时间: 1s
     */
    private static final long DEFAULT_TIMEOUT = 1_000L;
    /**
     * 超时阈值
     */
    private final long timeout;

    public TimeoutRetryPolicy() {
        this(DEFAULT_TIMEOUT);
    }

    public TimeoutRetryPolicy(long millis) {
        if (millis < 1) {
            throw new IllegalArgumentException("Timeout must be greater than 0");
        }
        this.timeout = millis;
    }

    public long getTimeout() {
        return timeout;
    }

    @Override
    public RetryContext beforeInitialization(RetryContext context) {
        return new TimeoutRetryContext(context, timeout);
    }

    @Override
    public boolean allowRetry(RetryContext context) {
        return ((TimeoutRetryContext) context).isAlive();
    }

    private static class TimeoutRetryContext extends RetryContextSupport {

        private final long timeoutMillis;

        private final long startTime;

        private TimeoutRetryContext(RetryContext context, long millis) {
            super(context);
            this.timeoutMillis = millis;
            this.startTime = System.currentTimeMillis();
        }

        public boolean isAlive() {
            return startTime + timeoutMillis > System.currentTimeMillis();
        }
    }
}
