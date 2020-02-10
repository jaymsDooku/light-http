package io.jayms.http.light.interfaces;

public interface HTTPRequestHandler {

    HTTPResponse handle(HTTPRequest request);

}
