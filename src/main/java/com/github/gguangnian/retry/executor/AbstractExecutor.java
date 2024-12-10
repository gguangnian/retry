package com.github.gguangnian.retry.executor;

import com.github.gguangnian.retry.RetryCallable;
import com.github.gguangnian.retry.context.RetryContext;
import com.github.gguangnian.retry.context.RetryContextSupport;
import com.github.gguangnian.retry.exception.ExhaustedRetryException;
import com.github.gguangnian.retry.exception.TerminatedRetryException;
import com.github.gguangnian.retry.support.AfterRetry;
import com.github.gguangnian.retry.support.RetryContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 实现接口{@link Executor}
 * <p>当且仅当{@code RetryCallable}未抛出异常时结束重试</p>
 */
public abstract class AbstractExecutor implements Executor {

    private static final Logger logger = LoggerFactory.getLogger(AbstractExecutor.class);

    @Override
    public <T> T execute(RetryCallable<T> retry) {
        Objects.requireNonNull(retry, "Retry callable must not be null");
        return doExecute(retry);
    }

    /**
     * 重复调用{@code RetryCallable}直到未抛出异常并返回返回值
     *
     * @param retry 重试任务
     * @param <T>   返回值类型
     * @return 返回值
     */
    protected <T> T doExecute(RetryCallable<T> retry) {
        RetryContext context = createRetryContextInstance();

        if (logger.isTraceEnabled()) {
            logger.trace("Creating retry context: {}", context);
        }

        context = prepareRetryContext(context);

        return doExecute(retry, context);
    }

    /**
     * 重复调用{@code RetryCallable}直到未抛出异常并返回返回值
     *
     * @param retry   重试任务
     * @param context 重试上下文
     * @param <T>     返回值类型
     * @return 返回值
     * @throws TerminatedRetryException 线程中断
     * @throws ExhaustedRetryException  重试资源耗尽
     */
    protected <T> T doExecute(RetryCallable<T> retry, RetryContext context) {
        Objects.requireNonNull(context, "Retry context must not be null");

        RetryContextHolder.setRetryContext(context);

        if (logger.isTraceEnabled()) {
            logger.trace("Preparing retry context: {}", context);
        }

        try {

            while (!Thread.currentThread().isInterrupted()) {

                if (context.isExhaustedOnly() || determineTerminate(context)) {
                    break;
                }

                if (logger.isTraceEnabled()) {
                    logger.trace("NO.{} Examining retry context: {}", context.getRetryCount(), context);
                }

                try {
                    T returnObject = retry.callWithRetry();
                    if (logger.isTraceEnabled()) {
                        logger.trace("NO.{} Invoking retry callback of {} succeed by retry context {}. Result: {}", context.getRetryCount(), retry, context, returnObject);
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("NO.{} Invoking retry callback of {} succeed.", context.getRetryCount(), retry);
                    }
                    return invokeAfterReturning(returnObject, retry);
                } catch (Exception e) {
                    if (logger.isTraceEnabled()) {
                        logger.trace(String.format("NO.%s Invoking retry callback of %s failed by retry context: %s. Error: %s", context.getRetryCount(), retry, context, e.getMessage()), e);
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("NO.%s Invoking retry callback of %s failed. ", context.getRetryCount(), retry));
                    }
                    try {
                        invokeAfterThrowing(e, retry);
                    } catch (Exception e2) {
                        e.addSuppressed(e2);
                    }
                    processThrowing(e, context);
                } finally {
                    incrementRetryCount(context);
                }


            }

            if (Thread.currentThread().isInterrupted()) {
                throw new TerminatedRetryException("Retry terminated abnormally by thread interrupt");
            }

            throw new ExhaustedRetryException("Retry exhausted after last attempt");

        } finally {
            try {
                if (logger.isTraceEnabled()) {
                    logger.trace("Closing retry context: {}", context);
                }
                closeRetryContext(context);
            } finally {
                RetryContextHolder.removeRetryContext();
            }
        }
    }

    /**
     * 创建默认重试上下文
     *
     * @return 重试上下文
     */
    protected RetryContext createRetryContextInstance() {
        return new RetryContextSupport();
    }

    /**
     * 调用{@code afterThrowing}
     *
     * @param exception 异常
     * @param target    被调用
     * @param <T>       返回值类型
     */
    protected <T> void invokeAfterThrowing(Exception exception, RetryCallable<T> target) {
        if (target instanceof AfterRetry) {
            ((AfterRetry) target).afterThrowing(exception);
        }
    }

    /**
     * 调用{@code afterReturning}
     *
     * @param returnValue 原始返回值
     * @param target      被调用
     * @param <T>         返回值类型
     * @return 返回值
     */
    protected <T> T invokeAfterReturning(T returnValue, RetryCallable<T> target) {
        if (target instanceof AfterRetry) {
            return ((AfterRetry) target).afterReturning(returnValue);
        }
        return returnValue;
    }

    /**
     * 重试上下文次数加一
     *
     * @param context 重试上下文
     */
    protected void incrementRetryCount(RetryContext context) {
        ((RetryContextSupport) context).incrementRetryCount();
    }


    /**
     * 预处理重试上下文
     *
     * @param context 重试上下文
     * @return 实际重试上下文
     */
    protected abstract RetryContext prepareRetryContext(RetryContext context);

    /**
     * 终止重试
     *
     * @param context 重试上下文
     * @return {@code true}终止重试
     */
    protected abstract boolean determineTerminate(RetryContext context);

    /**
     * 处理单次尝试抛出的异常
     * <p>仅记录日志</p>
     *
     * @param e       异常
     * @param context 重试上下文
     */
    protected abstract void processThrowing(Exception e, RetryContext context);

    /**
     * 关闭重试上下文
     *
     * @param context 重试上下文
     */
    protected abstract void closeRetryContext(RetryContext context);
}
