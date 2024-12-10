package com.github.gguangnian.retry.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 实现接口{@link AttributeAccessor}
 */
public class AttributeAccessorSupport implements AttributeAccessor {

    private final Map<String, Object> attributes = new HashMap<>();

    @Override
    public void setAttribute(String name, Object value) {
        Objects.requireNonNull(name, "Name must not be null");
        if (value != null) {
            attributes.put(name, value);
        } else {
            this.removeAttribute(name);
        }
    }

    @Override
    public Object getAttribute(String name) {
        Objects.requireNonNull(name, "Name must not be null");
        return attributes.get(name);
    }

    @Override
    public Object removeAttribute(String name) {
        Objects.requireNonNull(name, "Name must not be null");
        return attributes.remove(name);
    }

    @Override
    public boolean hasAttribute(String name) {
        Objects.requireNonNull(name, "Name must not be null");
        return attributes.containsKey(name);
    }

    @Override
    public String[] attributeNames() {
        return attributes.keySet().toArray(new String[0]);
    }

    @Override
    public String toString() {
        return "AttributeAccessorSupport{" +
                "attributes=" + attributes +
                '}';
    }
}
