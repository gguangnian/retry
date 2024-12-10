package com.github.gguangnian.retry.support;

import com.github.gguangnian.retry.context.RetryContext;

import java.util.Objects;

/**
 * 持有重试上下文
 */
public final class RetryContextHolder {

    private volatile static RetryContextHolder INSTANCE;

    private final ThreadLocal<RetryContext> retryContextHolder = new ThreadLocal<>();

    private static RetryContextHolder getInstance() {
        if (INSTANCE == null) {
            synchronized (RetryContextHolder.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetryContextHolder();
                }
            }
        }
        return INSTANCE;
    }

    private RetryContextHolder() {
    }

    /**
     * 获取重试上下文
     *
     * @return 重试上下文
     */
    public static RetryContext getRetryContext() {
        RetryContext context = getInstance().retryContextHolder.get();
        Objects.requireNonNull(context, "Retry context must not be null");
        return context;
    }

    /**
     * 设置重试上下文
     *
     * @param context 重试上下文
     */
    public static void setRetryContext(RetryContext context) {
        Objects.requireNonNull(context, "Retry context must not be null");
        RetryContextHolder.getInstance().retryContextHolder.set(context);
    }

    /**
     * 清除重试上下文
     */
    public static void removeRetryContext() {
        RetryContextHolder.getInstance().retryContextHolder.remove();
    }
}
