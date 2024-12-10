package com.github.gguangnian.retry.policy;

import com.github.gguangnian.retry.context.RetryContext;

/**
 * 重试策略
 */
public interface RetryPolicy {

    /**
     * 在{@code InitializingRetry}初始化前调用
     *
     * @param context 重试山下文
     * @return 重试山下文
     */
    RetryContext beforeInitialization(RetryContext context);

    /**
     * 允许继续重试
     *
     * @param context 重试上下文
     * @return {@code true}允许
     */
    boolean allowRetry(RetryContext context);

    /**
     * 处理异常
     *
     * @param exception 异常
     * @param context   重试上下文
     */
    void processThrowing(Exception exception, RetryContext context);

    /**
     * 在{@code DestroyRetry}销毁前调用
     *
     * @param context 重试上下文
     */
    void beforeDestruction(RetryContext context);
}
