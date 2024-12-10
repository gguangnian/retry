package com.github.gguangnian.retry;

/**
 * 可重试方法接口
 *
 * @param <T> 返回值类型
 */
public interface RetryCallable<T> {
    /**
     * 可重试方法
     *
     * @return 返回值
     * @throws Exception 可能抛出的异常
     */
    T callWithRetry() throws Exception;
}
