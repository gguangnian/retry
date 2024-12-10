package com.github.gguangnian.retry.context;

import com.github.gguangnian.retry.support.AttributeAccessor;

/**
 * 重试上下文
 * <p>每一个{@code RetryCallable}都存在一个对应的{@code RetryContext}</p>
 */
public interface RetryContext extends AttributeAccessor {

    /**
     * 获取父上下文
     *
     * @return 父上下文
     */
    RetryContext getParentContext();

    /**
     * 是否重试资源耗尽
     *
     * @return {@code true}耗尽
     */
    boolean isExhaustedOnly();

    /**
     * 设置重试资源耗尽
     * <p>用户结束循环的方法</p>
     */
    void setExhaustedOnly();

    /**
     * 获取重试次数
     * <p>只有返回返回值或抛出异常时才视为完成一次调用</p>
     *
     * @return 次数
     */
    int getRetryCount();
}
