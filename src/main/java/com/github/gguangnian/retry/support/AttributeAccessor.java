package com.github.gguangnian.retry.support;

/**
 * 附加和访问数据的接口
 */
public interface AttributeAccessor {

    /**
     * 将属性键{@code name}对应的属性值设置为{@code value}
     * <p>如果{@code value}为{@code null}则删除此属性</p>
     *
     * @param name  唯一的属性键
     * @param value 属性值
     */
    void setAttribute(String name, Object value);

    /**
     * 访问属性键{@code name}对应的属性值
     * <p>如果属性不存在返回{@code null}</p>
     *
     * @param name 唯一的属性键
     * @return 属性值或{@code null}
     */
    Object getAttribute(String name);

    /**
     * 删除属性键{@code name}对应的属性值
     * <p>如果删除的属性不存在返回{@code null}</p>
     *
     * @param name 唯一的属性键
     * @return 删除的属性值或{@code null}
     */
    Object removeAttribute(String name);

    /**
     * 是否存在属性
     *
     * @param name 唯一的属性键
     * @return {@code true}存在, {@code false}不存在
     */
    boolean hasAttribute(String name);

    /**
     * 访问所有的属性键
     *
     * @return 所有的属性键
     */
    String[] attributeNames();
}
