package com.github.gguangnian.retry.support;

import com.github.gguangnian.retry.executor.DefaultExecutor;
import com.github.gguangnian.retry.executor.Executor;
import com.github.gguangnian.retry.interval.FixedIntervalPolicy;
import com.github.gguangnian.retry.interval.IntervalPolicy;
import com.github.gguangnian.retry.interval.NoIntervalPolicy;
import com.github.gguangnian.retry.policy.AlwaysRetryPolicy;
import com.github.gguangnian.retry.policy.MaxAttemptsRetryPolicy;
import com.github.gguangnian.retry.policy.RetryPolicy;
import com.github.gguangnian.retry.policy.TimeoutRetryPolicy;

import java.util.Objects;

/**
 * 重试器
 */
public final class Retryer {

    public static RetryerBuilder builder() {
        return new RetryerBuilder();
    }

    public static class RetryerBuilder {

        private RetryPolicy baseRetryPolicy;

        private IntervalPolicy baseIntervalPolicy;

        /**
         * 永远重试
         *
         * @return 构建器
         */
        public RetryerBuilder infiniteRetry() {
            if (baseRetryPolicy != null) {
                throw new IllegalStateException("You have already selected another retry policy");
            }
            this.baseRetryPolicy = new AlwaysRetryPolicy();
            return this;
        }

        /**
         * 最大重试次数重试
         *
         * @param maxAttempts 最大次数
         * @return 构建器
         */
        public RetryerBuilder maxAttempts(int maxAttempts) {
            if (baseRetryPolicy != null) {
                throw new IllegalStateException("You have already selected another retry policy");
            }
            this.baseRetryPolicy = new MaxAttemptsRetryPolicy(maxAttempts);
            return this;
        }

        /**
         * 最大重试时间
         *
         * @param millis 毫秒
         * @return 构建器
         */
        public RetryerBuilder withMillis(int millis) {
            if (baseRetryPolicy != null) {
                throw new IllegalStateException("You have already selected another retry policy");
            }
            this.baseRetryPolicy = new TimeoutRetryPolicy(millis);
            return this;
        }

        /**
         * 自定义重试策略
         *
         * @param retryPolicy 重试策略
         * @return 构建器
         */
        public RetryerBuilder customRetryPolicy(RetryPolicy retryPolicy) {
            Objects.requireNonNull(retryPolicy, "Retry policy must not be null");
            if (baseRetryPolicy != null) {
                throw new IllegalStateException("You have already selected another retry policy");
            }
            this.baseRetryPolicy = retryPolicy;
            return this;
        }

        /**
         * 不中断
         *
         * @return 构建器
         */
        public RetryerBuilder noInterval() {
            if (baseIntervalPolicy != null) {
                throw new IllegalStateException("You have already selected another interval policy");
            }
            this.baseIntervalPolicy = new NoIntervalPolicy();
            return this;
        }

        /**
         * 固定时间中断
         *
         * @param millis 毫秒数
         * @return 构建器
         */
        public RetryerBuilder fixedInterval(long millis) {
            if (baseIntervalPolicy != null) {
                throw new IllegalStateException("You have already selected another interval policy");
            }
            this.baseIntervalPolicy = new FixedIntervalPolicy(millis);
            return this;
        }


        /**
         * 自定义中断策略
         *
         * @param intervalPolicy 中断策略
         * @return 构建器
         */
        public RetryerBuilder customIntervalPolicy(IntervalPolicy intervalPolicy) {
            Objects.requireNonNull(intervalPolicy, "Interval policy must not be null");
            if (baseIntervalPolicy != null) {
                throw new IllegalStateException("You have already selected another interval policy");
            }
            this.baseIntervalPolicy = intervalPolicy;
            return this;
        }


        public Executor build() {
            Objects.requireNonNull(baseRetryPolicy, "Retry policy must not be null");
            if (baseIntervalPolicy != null) {
                return new DefaultExecutor(baseRetryPolicy, baseIntervalPolicy);
            }
            return new DefaultExecutor(baseRetryPolicy);
        }
    }
}
