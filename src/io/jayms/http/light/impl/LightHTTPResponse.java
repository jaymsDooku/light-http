package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPResponse;
import io.jayms.http.light.interfaces.HTTPStatusCode;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

public class LightHTTPResponse<T> extends LightHTTPPayload<T> implements HTTPResponse<T> {

    private HTTPStatusCode statusCode;

    private LightHTTPResponse(LightHTTPResponseBuilder<T> builder) {
        super(builder.address, builder.header, builder.version, builder.body);
        this.statusCode = builder.statusCode;
    }

    @Override
    public HTTPStatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public ByteBuffer encode() {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(getVersion());
        headerBuilder.append(" ");

        HTTPStatusCode statusCode = getStatusCode();
        headerBuilder.append(statusCode.code());
        headerBuilder.append(" ");
        headerBuilder.append(statusCode.toString());

        return super.encode();
    }

    public static LightHTTPResponseBuilder builder(SocketAddress address, String version) {
        return new LightHTTPResponseBuilder(address, version);
    }

    public static class LightHTTPResponseBuilder<T> {

        private SocketAddress address;
        private Map<String, Object> header;
        private String version;
        private T body;
        private HTTPStatusCode statusCode = HTTPStatusCode.OK;

        LightHTTPResponseBuilder(SocketAddress address, String version) {
            this.address = address;
            this.version = version;
        }

        public LightHTTPResponseBuilder header(Map<String, Object> header) {
            this.header = header;
            return this;
        }

        public LightHTTPResponseBuilder body(T body) {
            this.body = body;
            return this;
        }

        public LightHTTPResponseBuilder statusCode(HTTPStatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public LightHTTPResponse<T> build() {
            return new LightHTTPResponse<>(this);
        }

    }

}
