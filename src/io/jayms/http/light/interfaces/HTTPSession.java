package io.jayms.http.light.interfaces;

import java.util.List;

public interface HTTPSession {

    void putRequest(HTTPRequest request);

    HTTPRequest popRequest();

    void putResponse(HTTPResponse response);

    HTTPResponse pollResponse();

}
