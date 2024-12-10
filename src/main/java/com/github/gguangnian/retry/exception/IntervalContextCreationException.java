package com.github.gguangnian.retry.exception;

/**
 * 中断上下文创建异常
 */
public class IntervalContextCreationException extends RetryException {

    public IntervalContextCreationException() {
        super();
    }

    public IntervalContextCreationException(String message) {
        super(message);
    }

    public IntervalContextCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
