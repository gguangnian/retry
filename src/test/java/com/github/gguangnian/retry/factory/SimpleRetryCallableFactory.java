package com.github.gguangnian.retry.factory;

import com.github.gguangnian.retry.RetryCallable;
import com.github.gguangnian.retry.support.RetryContextHolder;

/**
 *
 */
public final class SimpleRetryCallableFactory {
    /**
     * 成功的返回值
     */
    public static final String SUCCEED_RESULT = "SUCCEED_RESULT";

    /**
     * 失败的抛出异常消息
     */
    public static final String FAILED_THROWABLE_MESSAGE = "FAILED_MESSAGE";

    /**
     * 立刻成功返回返回值
     *
     * @return 重试任务
     */
    public static RetryCallable<String> onSuccessFast() {
        return () -> SUCCEED_RESULT;
    }

    /**
     * 立刻失败抛出异常
     *
     * @return 重试任务
     */
    public static RetryCallable<String> onFailFast() {
        return () -> {
            throw new IllegalStateException(FAILED_THROWABLE_MESSAGE);
        };
    }

    /**
     * 大于次数成功
     *
     * @param greater 次数
     * @return 重试任务
     */
    public static RetryCallable<String> onSuccessGreater(int greater) {
        return () -> {
            if (RetryContextHolder.getRetryContext().getRetryCount() >= greater) {
                return SUCCEED_RESULT;
            }
            throw new IllegalStateException(FAILED_THROWABLE_MESSAGE);
        };
    }
}
