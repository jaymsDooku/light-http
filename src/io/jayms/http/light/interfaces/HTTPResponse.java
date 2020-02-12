package io.jayms.http.light.interfaces;

public interface HTTPResponse<T> extends HTTPPayload<T> {

    HTTPStatusCode getStatusCode();

}
