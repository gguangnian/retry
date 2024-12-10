package com.github.gguangnian.retry.policy;

import com.github.gguangnian.retry.context.RetryContext;

/**
 * 永远重试, 不会停止
 */
public class AlwaysRetryPolicy extends RetryPolicyAdapter {

    @Override
    public boolean allowRetry(RetryContext context) {
        return true;
    }
}
