package io.jayms.http.light.interfaces;

import java.net.SocketAddress;
import java.util.List;

public interface HTTPSession {

    SocketAddress getAddress();

    void putRequest(HTTPRequest request);

    HTTPRequest popRequest();

    void putResponse(HTTPResponse response);

    HTTPResponse pollResponse();

}
