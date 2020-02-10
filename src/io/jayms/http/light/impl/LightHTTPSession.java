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

    public void putRequest(HTTPRequest request) {
        requestQueue.add(request);
    }

    public HTTPRequest popRequest() {
        return requestQueue.poll();
    }

    public void putResponse(HTTPResponse response) {
        responseQueue.add(response);
    }

    public HTTPResponse pollResponse() {
        return responseQueue.poll();
    }

}
