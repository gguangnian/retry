package com.github.gguangnian.retry.executor;

import com.github.gguangnian.retry.context.RetryContext;
import com.github.gguangnian.retry.exception.IntervalContextCreationException;
import com.github.gguangnian.retry.exception.TerminatedRetryException;
import com.github.gguangnian.retry.interval.IntervalContext;
import com.github.gguangnian.retry.interval.IntervalPolicy;
import com.github.gguangnian.retry.interval.NoIntervalPolicy;
import com.github.gguangnian.retry.policy.NeverRetryPolicy;
import com.github.gguangnian.retry.policy.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 实现接口{@link ConfigurableExecutor}
 */
public abstract class AbstractConfigurableExecutor extends AbstractExecutor implements ConfigurableExecutor {

    private static final Logger logger = LoggerFactory.getLogger(AbstractConfigurableExecutor.class);

    private static final String KEY_INTERVAL_CONTEXT = "INTERVAL_CONTEXT";
    /**
     * 默认重试策略
     */
    private static final RetryPolicy DEFAULT_RETRY_POLICY = new NeverRetryPolicy();
    /**
     * 默认间隔策略
     */
    private static final IntervalPolicy DEFAULT_INTERVAL_POLICY = new NoIntervalPolicy();

    private RetryPolicy retryPolicy;

    private IntervalPolicy intervalPolicy;

    public AbstractConfigurableExecutor() {
    }


    @Override
    public RetryPolicy getRetryPolicy() {
        return this.retryPolicy == null ? DEFAULT_RETRY_POLICY : this.retryPolicy;
    }

    @Override
    public void setRetryPolicy(RetryPolicy retryPolicy) {
        if (this.retryPolicy != null) {
            throw new IllegalStateException("You have already selected another retry policy");
        }
        Objects.requireNonNull(retryPolicy, "Retry policy must not be null");
        this.retryPolicy = retryPolicy;
    }

    @Override
    public IntervalPolicy getIntervalPolicy() {
        return this.intervalPolicy;
    }

    @Override
    public void setIntervalPolicy(IntervalPolicy intervalPolicy) {
        if (this.intervalPolicy != null) {
            throw new IllegalStateException("You have already selected another interval policy");
        }
        Objects.requireNonNull(intervalPolicy, "Interval policy must not be null");
        this.intervalPolicy = intervalPolicy;
    }

    /**
     * 重试策略加工重试上下文
     *
     * @param context 重试上下文
     * @return 重试上下文
     */
    @Override
    protected RetryContext prepareRetryContext(RetryContext context) {
        RetryContext result = getRetryPolicy().beforeInitialization(context);

        doIntervalContext(context);

        return result == null ? context : result;
    }

    /**
     * 重试策略是否需要终止
     *
     * @param context 重试上下文
     * @return {@code true}终止
     */
    @Override
    protected boolean determineTerminate(RetryContext context) {
        return !getRetryPolicy().allowRetry(context);
    }

    /**
     * 应用重试策略方法{@code processThrowing}
     *
     * @param context 重试上下文
     */
    @Override
    protected void processThrowing(Exception e, RetryContext context) {
        getRetryPolicy().processThrowing(e, context);

        doInterval(context);
    }

    /**
     * 应用重试策略方法{@code beforeDestruction}
     *
     * @param context 重试上下文
     */
    @Override
    protected void closeRetryContext(RetryContext context) {
        getRetryPolicy().beforeDestruction(context);
    }

    /**
     * 创建中断上下文
     *
     * @param context 重试上下文
     */
    private void doIntervalContext(RetryContext context) {
        if (getIntervalPolicy() == null) {
            return;
        }
        try {
            IntervalContext intervalContext = getIntervalPolicy().create(context);
            Objects.requireNonNull(intervalContext, "Interval context must not be null");
            context.setAttribute(KEY_INTERVAL_CONTEXT, intervalContext);
        } catch (Exception ex) {
            throw new IntervalContextCreationException("Create interval context failed from retry context: " + context, ex);
        }
    }

    /**
     * 线程未中断 且重试未耗尽时执行中断策略
     *
     * @param context 重试上下文
     */
    private void doInterval(RetryContext context) {
        if (getIntervalPolicy() == null) {
            return;
        }
        if (!context.hasAttribute(KEY_INTERVAL_CONTEXT)) {
            throw new TerminatedRetryException("Retry terminated abnormally by not find interval context");
        }
        IntervalContext intervalContext = (IntervalContext) context.getAttribute(KEY_INTERVAL_CONTEXT);

        if (!Thread.currentThread().isInterrupted() && !context.isExhaustedOnly() && !determineTerminate(context)) {
            try {
                if (logger.isTraceEnabled()) {
                    logger.trace("NO.{} Starting retry interval.", context.getRetryCount());
                }
                getIntervalPolicy().interval(intervalContext);
                if (logger.isTraceEnabled()) {
                    logger.trace("NO.{} Stopping retry interval.", context.getRetryCount());
                }
            } catch (Exception e) {
                throw new TerminatedRetryException("Retry terminated abnormally by interval policy: " + getIntervalPolicy(), e);
            }
        }
    }
}
