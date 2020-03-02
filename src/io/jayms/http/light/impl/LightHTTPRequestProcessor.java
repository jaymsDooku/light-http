package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.*;

import java.net.SocketAddress;
import java.util.*;

public class LightHTTPRequestProcessor extends Thread {

    private HTTPContext context;
    private HTTPPayloadManager sessionManager;

    public LightHTTPRequestProcessor(HTTPServer server) {
        super("LightHTTPRequestProcessor");
        this.sessionManager = server.sessionManager();
        this.context = server.context();
    }

    @Override
    public void run() {
        while (true) {
            Map<SocketAddress, HTTPSession> sessionMap = sessionManager.getSessionMap();
            if (sessionMap.isEmpty()) continue;

            Set<SocketAddress> removeKeys = new HashSet<>();
            Set<SocketAddress> allKeys = sessionMap.keySet();
            for (SocketAddress address : allKeys) {
                HTTPSession session = sessionMap.get(address);

                HTTPRequest request = session.peekRequest();
                if (request == null) {
                    continue;
                }

                HTTPLocation location = request.getLocation();
                System.out.println("location: " + location);
                HTTPRequestHandler handler = context.getHandler(location);
                System.out.println("handler: " + handler);
                System.out.println("session: " + session);

                HTTPResponse response = handler.handle(request);
                session.putResponse(response);
                session.removeRequest(request);
                sessionManager.replaceSession(address, session);
            }

            /*for (SocketAddress removeKey : removeKeys) {
                System.out.println("terminate " + removeKey);
                sessionManager.terminate(removeKey);
            }*/
        }
    }
}
