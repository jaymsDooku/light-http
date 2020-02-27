package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.*;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;

public class LightHTTPRequestProcessor extends Thread {

    private HTTPContext context;
    private HTTPSessionManager sessionManager;

    public LightHTTPRequestProcessor(HTTPServer server) {
        this.sessionManager = server.sessionManager();
        this.context = server.context();
    }

    @Override
    public void run() {
        while (true) {
            Map<SocketAddress, HTTPSession> sessionMap = sessionManager.getSessionMap();
            if (sessionMap.isEmpty()) continue;

            Set<SocketAddress> removeKeys = new HashSet<>();
            Iterator<Map.Entry<SocketAddress, HTTPSession>> sessionIterator = sessionMap.entrySet().iterator();
            while (sessionIterator.hasNext()) {
                Map.Entry<SocketAddress, HTTPSession> sessionEntry = sessionIterator.next();
                HTTPSession session = sessionEntry.getValue();
                SocketAddress address = session.getAddress();

                HTTPRequest request = session.popRequest();
                if (request == null) {
                    //sessionIterator.remove();
                    continue;
                }

                HTTPLocation location = request.getLocation();
                System.out.println("location: " + location);
                HTTPRequestHandler handler = context.getHandler(location);
                System.out.println("handler: " + handler);
                System.out.println("session: " + session);
                if (handler == null) {
                    continue;
                }

                HTTPResponse response = handler.handle(request);
                session.putResponse(response);
                sessionManager.replaceSession(address, session);
                System.out.println("response: " + response);
                System.out.println("session: " + session);
                System.out.println("session2: " + sessionManager.getSession(address));
            }

            /*for (SocketAddress removeKey : removeKeys) {
                System.out.println("terminate " + removeKey);
                sessionManager.terminate(removeKey);
            }*/
        }
    }
}
