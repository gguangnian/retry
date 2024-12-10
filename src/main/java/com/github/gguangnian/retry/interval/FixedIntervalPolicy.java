package com.github.gguangnian.retry.interval;

import com.github.gguangnian.retry.context.RetryContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 固定时间间隔
 */
public class FixedIntervalPolicy implements IntervalPolicy {

    private static final Logger logger = LoggerFactory.getLogger(FixedIntervalPolicy.class);
    /**
     * 默认间隔时间: 1s
     */
    private static final long DEFAULT_INTERVAL = 1_000L;

    /**
     * 间隔时间
     */
    private final long intervalMillis;

    public FixedIntervalPolicy() {
        this(DEFAULT_INTERVAL);
    }

    public FixedIntervalPolicy(long millis) {
        if (millis < 1) {
            throw new IllegalArgumentException("Interval duration must be greater than 0");
        }
        this.intervalMillis = millis;
    }


    public long getIntervalMillis() {
        return intervalMillis;
    }


    @Override
    public IntervalContext create(RetryContext context) {
        return new FixedIntervalContext();
    }

    @Override
    public void interval(IntervalContext context) {
        try {
            Thread.sleep(this.intervalMillis);
        } catch (InterruptedException e) {
            logger.warn(e.getMessage(), e);
            // nothing
        }
    }

    private static class FixedIntervalContext implements IntervalContext {

    }
}
