package com.github.gguangnian.retry.exception;

/**
 * 终止重试异常
 */
public class TerminatedRetryException extends RetryException{

    public TerminatedRetryException() {
    }

    public TerminatedRetryException(String message) {
        super(message);
    }

    public TerminatedRetryException(String message, Throwable cause) {
        super(message, cause);
    }
}
