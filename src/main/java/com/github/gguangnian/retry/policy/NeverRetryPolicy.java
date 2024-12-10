package com.github.gguangnian.retry.policy;

import com.github.gguangnian.retry.context.RetryContext;
import com.github.gguangnian.retry.context.RetryContextSupport;

/**
 * 永不重试
 */
public class NeverRetryPolicy extends RetryPolicyAdapter {

    @Override
    public RetryContext beforeInitialization(RetryContext context) {
        return new NeverRetryContext(context);
    }

    @Override
    public boolean allowRetry(RetryContext context) {
        return !((NeverRetryContext) context).isTrigger();
    }

    @Override
    public void processThrowing(Exception exception, RetryContext context) {
        ((NeverRetryContext) context).setTrigger();
    }

    /**
     * 永不重试重试上下文
     */
    private static class NeverRetryContext extends RetryContextSupport {

        public NeverRetryContext(RetryContext parentContext) {
            super(parentContext);
        }

        /**
         * 触发
         */
        private boolean trigger = false;

        public boolean isTrigger() {
            return trigger;
        }

        public void setTrigger() {
            this.trigger = true;
        }
    }
}
