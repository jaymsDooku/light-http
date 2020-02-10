package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPRequest;
import io.jayms.http.light.interfaces.HTTPResponse;
import io.jayms.http.light.interfaces.HTTPSession;

import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LightHTTPSession implements HTTPSession {

    private SocketAddress address;
    private Queue<HTTPRequest> requestQueue;
    private Queue<HTTPResponse> responseQueue;

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
        requestQueue.add(request);
    }

    @Override
    public HTTPRequest popRequest() {
        return requestQueue.poll();
    }

    @Override
    public void putResponse(HTTPResponse response) {
        responseQueue.add(response);
    }

    @Override
    public HTTPResponse pollResponse() {
        return responseQueue.poll();
    }

}
