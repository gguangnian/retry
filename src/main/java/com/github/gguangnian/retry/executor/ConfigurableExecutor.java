package com.github.gguangnian.retry.executor;

import com.github.gguangnian.retry.interval.IntervalPolicy;
import com.github.gguangnian.retry.policy.RetryPolicy;

/**
 * 可配置执行器
 */
public interface ConfigurableExecutor extends Executor{
    /**
     * 获取重试策略
     *
     * @return 重试策略
     */
    RetryPolicy getRetryPolicy();

    /**
     * 设置重试策略
     *
     * @param retryPolicy 重试策略
     */
    void setRetryPolicy(RetryPolicy retryPolicy);

    /**
     * 获取间隔策略
     *
     * @return 间隔策略
     */
    IntervalPolicy getIntervalPolicy();

    /**
     * 设置间隔策略
     *
     * @param intervalPolicy 间隔策略
     */
    void setIntervalPolicy(IntervalPolicy intervalPolicy);
}
