package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPPayload;

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

}
