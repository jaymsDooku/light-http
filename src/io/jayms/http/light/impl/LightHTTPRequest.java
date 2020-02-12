package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPMethod;
import io.jayms.http.light.interfaces.HTTPRequest;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;

public class LightHTTPRequest<T> extends LightHTTPPayload<T> implements HTTPRequest<T> {

    private String path;
    private HTTPMethod method;

    private LightHTTPRequest(LightHTTPRequestBuilder<T> builder) {
        super(builder.address, builder.header, builder.version, builder.body);
        this.path = builder.path;
        this.method = builder.method;
    }

    @Override
    public HTTPMethod getMethod() {
        return method;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "LightHTTPRequest{" +
                "path='" + path + '\'' +
                ", method=" + method +
                '}';
    }

    public static LightHTTPRequestBuilder builder(SocketAddress address, String version) {
        return new LightHTTPRequestBuilder(address, version);
    }

    public static class LightHTTPRequestBuilder<T> {

        private SocketAddress address;
        private Map<String, Object> header;
        private String version;
        private T body;
        private String path;
        private HTTPMethod method;

        LightHTTPRequestBuilder(SocketAddress address, String version) {
            this.address = address;
            this.version = version;
        }

        public LightHTTPRequestBuilder header(Map<String, Object> header) {
            this.header = header;
            return this;
        }

        public LightHTTPRequestBuilder body(T body) {
            this.body = body;
            return this;
        }

        public LightHTTPRequestBuilder path(String path) {
            this.path = path;
            return this;
        }

        public LightHTTPRequestBuilder method(HTTPMethod method) {
            this.method = method;
            return this;
        }

        public LightHTTPRequest build() {
            return new LightHTTPRequest(this);
        }

    }

}
