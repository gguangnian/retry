package com.github.gguangnian.retry.support;

/**
 * 每次重复调用后
 */
public interface AfterRetry {

    /**
     * 如果调用返回返回值
     *
     * @param returnValue 返回值
     * @return 最终返回值
     */
    <T> T afterReturning(T returnValue);

    /**
     * 如果调用抛出异常
     *
     * @param exception 抛出的异常
     */
    void afterThrowing(Exception exception);
}
