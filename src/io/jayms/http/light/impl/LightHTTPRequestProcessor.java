package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.*;

import java.net.SocketAddress;
import java.util.*;

public class LightHTTPRequestProcessor extends Thread {

    private HTTPSessionManager sessionManager;
    private Map<HTTPMethod, HTTPRequestHandler> requestHandlerMap;

    public LightHTTPRequestProcessor(HTTPServer server) {
        this.sessionManager = server.sessionManager();
        this.requestHandlerMap = new EnumMap<HTTPMethod, HTTPRequestHandler>(HTTPMethod.class);
    }

    public void registerRequestHandler(HTTPMethod method, HTTPRequestHandler httpRequestHandler) {
        requestHandlerMap.put(method, httpRequestHandler);
    }

    @Override
    public void run() {
        while (true) {
            Collection<HTTPSession> sessionCollection = sessionManager.getSessions();
            if (sessionCollection.isEmpty()) continue;

            Set<SocketAddress> removeKeys = new HashSet<>();
            Iterator<HTTPSession> sessionIterator = sessionCollection.iterator();
            while (sessionIterator.hasNext()) {
                HTTPSession session = sessionIterator.next();
                SocketAddress address = session.getAddress();
                sessionIterator.remove();

                HTTPRequest request = session.popRequest();
                if (request == null) {
                    removeKeys.add(address);
                    continue;
                }

                HTTPRequestHandler handler = requestHandlerMap.get(request.getMethod());
                if (handler == null) continue;

                HTTPResponse response = handler.handle(request);
                session.putResponse(response);
            }

            for (SocketAddress removeKey : removeKeys) {
                sessionManager.terminate(removeKey);
            }
        }
    }
}
