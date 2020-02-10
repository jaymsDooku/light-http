package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPPayload;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

public class LightHTTPPayload<T> implements HTTPPayload<T> {

    private SocketAddress address;
    private Map<String, Object> header;
    private T body;

    public LightHTTPPayload(SocketAddress address, Map<String, Object> header, T body) {
        this.address = address;
        this.header = header;
        this.body = body;
    }

    @Override
    public SocketAddress getAddress() {
        return address;
    }

    @Override
    public Map<String, Object> getHeader() {
        return header;
    }

    @Override
    public T getBody() {
        return body;
    }

    @Override
    public ByteBuffer encode() {
        return null;
    }

    @Override
    public String toString() {
        return "LightHTTPPayload{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
