package com.github.gguangnian.retry.executor;

import com.github.gguangnian.retry.RetryCallable;

/**
 * 重试执行器
 */
public interface Executor {

    /**
     * 执行
     *
     * @param retry 重试任务
     * @param <T>   返回值类型
     * @return 返回值
     */
    <T> T execute(RetryCallable<T> retry);
}
