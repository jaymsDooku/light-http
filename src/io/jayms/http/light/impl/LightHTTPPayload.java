package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPPayload;

import java.nio.ByteBuffer;
import java.util.Map;

public class LightHTTPPayload<T> implements HTTPPayload<T> {

    @Override
    public Map<String, Object> getHeader() {
        return null;
    }

    @Override
    public T getBody() {
        return null;
    }

    @Override
    public ByteBuffer encode() {
        return null;
    }
}
