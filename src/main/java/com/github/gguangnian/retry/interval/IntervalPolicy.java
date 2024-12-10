package com.github.gguangnian.retry.interval;

import com.github.gguangnian.retry.context.RetryContext;

/**
 * 重试间隔策略
 */
public interface IntervalPolicy {

    /**
     * 根据重试上下文创建间隔上下文
     *
     * @param context 重试上下文
     * @return 间隔上下文
     */
    IntervalContext create(RetryContext context);

    /**
     * 任务间隔
     *
     * @param context 间隔上下文
     */
    void interval(IntervalContext context);
}
