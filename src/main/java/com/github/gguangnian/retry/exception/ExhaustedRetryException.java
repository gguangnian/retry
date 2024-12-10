package com.github.gguangnian.retry.exception;

/**
 * 重试耗尽异常
 */
public class ExhaustedRetryException extends RetryException {

    public ExhaustedRetryException() {
    }

    public ExhaustedRetryException(String message) {
        super(message);
    }

    public ExhaustedRetryException(String message, Throwable cause) {
        super(message, cause);
    }
}
