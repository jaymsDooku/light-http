package io.jayms.http.light.interfaces;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.List;

public interface HTTPSession {

    SocketAddress getAddress();

    void putRequest(HTTPRequest request);

    HTTPRequest popRequest();

    void putResponse(HTTPResponse response);

    HTTPResponse pollResponse();

    int responses();

    int requests();

    ByteBuffer getCurrentBuffer();

    void setCurrentBuffer(ByteBuffer buffer);

}
