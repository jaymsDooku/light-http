package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPRequest;
import io.jayms.http.light.interfaces.HTTPResponse;
import io.jayms.http.light.interfaces.HTTPSession;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LightHTTPSession implements HTTPSession {

    private SocketAddress address;
    private Queue<HTTPRequest> requestQueue;
    private Queue<HTTPResponse> responseQueue;
    private ByteBuffer currentBuffer;

    public LightHTTPSession(SocketAddress address) {
        this.address = address;
        this.requestQueue = new ConcurrentLinkedQueue<>();
        this.responseQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public SocketAddress getAddress() {
        return address;
    }

    @Override
    public void putRequest(HTTPRequest request) {
        requestQueue.offer(request);
    }

    @Override
    public HTTPRequest peekRequest() {
        return requestQueue.peek();
    }

    @Override
    public void removeRequest(HTTPRequest request) {
        requestQueue.remove(request);
    }

    @Override
    public void putResponse(HTTPResponse response) {
        responseQueue.offer(response);
    }

    @Override
    public HTTPResponse pollResponse() {
        return responseQueue.poll();
    }

    @Override
    public int responses() {
        return responseQueue.size();
    }

    @Override
    public int requests() {
        return requestQueue.size();
    }

    @Override
    public ByteBuffer getCurrentBuffer() {
        return currentBuffer;
    }

    @Override
    public void setCurrentBuffer(ByteBuffer currentBuffer) {
        this.currentBuffer = currentBuffer;
    }

    @Override
    public String toString() {
        return "LightHTTPSession{" +
                "address=" + address +
                ", requestQueue=" + requestQueue +
                ", responseQueue=" + responseQueue +
                '}';
    }
}
