package com.github.gguangnian.retry.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttributeAccessorSupportTest {

    @Test
    public void testAttributeAccessor() {
        final String key = "key";
        final String value = "value";
        AttributeAccessorSupport attributeAccessorSupport = new AttributeAccessorSupport();
        // 初始不存在
        Assertions.assertFalse(attributeAccessorSupport.hasAttribute(key));
        // 设置后存在
        attributeAccessorSupport.setAttribute(key, value);
        Assertions.assertTrue(attributeAccessorSupport.hasAttribute(key));
        Assertions.assertEquals(value, attributeAccessorSupport.getAttribute(key));
        // 删除后不存在
        Assertions.assertEquals(value, attributeAccessorSupport.removeAttribute(key));
        Assertions.assertFalse(attributeAccessorSupport.hasAttribute(key));
        // 设置null删除
        attributeAccessorSupport.setAttribute(key, value);
        Assertions.assertTrue(attributeAccessorSupport.hasAttribute(key));
        attributeAccessorSupport.setAttribute(key, null);
        Assertions.assertFalse(attributeAccessorSupport.hasAttribute(key));
        // 输出key
        Assertions.assertNotNull(attributeAccessorSupport.attributeNames());
        Assertions.assertEquals(0, attributeAccessorSupport.attributeNames().length);
    }
}