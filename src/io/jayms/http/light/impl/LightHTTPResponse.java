package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPHeaders;
import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPResponse;
import io.jayms.http.light.interfaces.HTTPStatusCode;
import io.jayms.http.light.interfaces.content.ContentType;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
        headerBuilder.append("\r\n");

        Map<String, Object> header = getHeader();
        header.put(HTTPHeaders.CONTENT_TYPE, getContentType().getText());

        T body = getBody();
        ContentType contentType = getContentType();
        System.out.println("body: " + body);
        ByteBuffer encodedPayload = contentType.getEncoder().encode(StandardCharsets.UTF_8, body);
        System.out.println("encodedPayload: " + Arrays.toString(encodedPayload.array()));

        int contentLength = encodedPayload.capacity();
        header.put(HTTPHeaders.CONTENT_LENGTH, contentLength);

        for (Map.Entry<String, Object> headerEntry : header.entrySet()) {
            String key = headerEntry.getKey();
            Object value = headerEntry.getValue();
            headerBuilder.append(key);
            headerBuilder.append(": ");
            headerBuilder.append(value);
            headerBuilder.append("\r\n");
        }
        headerBuilder.append("\r\n");

        String headerStr = headerBuilder.toString();
        System.out.println("headerStr: " + headerStr);
        ByteBuffer headerBuffer = StandardCharsets.US_ASCII.encode(headerStr);
        System.out.println("headerBuffer: " + Arrays.toString(headerBuffer.array()));

        headerBuffer.flip();
        encodedPayload.flip();

        ByteBuffer fullPayload = ByteBuffer.allocate(headerBuffer.capacity() + encodedPayload.capacity());
        fullPayload.put(headerBuffer.array());
        fullPayload.put(encodedPayload.array());
        fullPayload.flip();
        System.out.println("fullPayload: " + Arrays.toString(fullPayload.array()));
        return fullPayload;
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
