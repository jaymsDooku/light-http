package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPSessionManager;

public class LightHTTPRequestProcessor implements Runnable {

    private HTTPSessionManager sessionManager;

    public LightHTTPRequestProcessor(HTTPSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void run() {
        // TODO: process requests
    }
}
