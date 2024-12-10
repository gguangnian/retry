package com.github.gguangnian.retry.exception;

/**
 * 重试异常
 */
public class RetryException extends RuntimeException{

    public RetryException() {
        super();
    }

    public RetryException(String message) {
        super(message);
    }

    public RetryException(String message, Throwable cause) {
        super(message, cause);
    }
}
