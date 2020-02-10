package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPResponse;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

public class LightHTTPResponse<T> extends LightHTTPPayload<T> implements HTTPResponse<T> {

    private LightHTTPResponse(LightHTTPResponseBuilder<T> builder) {
        super(builder.address, builder.header, builder.body);
    }

    @Override
    public ByteBuffer encode() {
        return super.encode();
    }

    public static LightHTTPResponseBuilder builder(SocketAddress address) {
        return new LightHTTPResponseBuilder(address);
    }

    public static class LightHTTPResponseBuilder<T> {

        private SocketAddress address;
        private Map<String, Object> header;
        private T body;

        LightHTTPResponseBuilder(SocketAddress address) {
            this.address = address;
        }

        public LightHTTPResponseBuilder header(Map<String, Object> header) {
            this.header = header;
            return this;
        }

        public LightHTTPResponseBuilder body(T body) {
            this.body = body;
            return this;
        }

        public LightHTTPResponse<T> build() {
            return new LightHTTPResponse<>(this);
        }

    }

}
