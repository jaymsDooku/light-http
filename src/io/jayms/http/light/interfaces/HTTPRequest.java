package io.jayms.http.light.interfaces;

public interface HTTPRequest<T> extends HTTPPayload<T> {

    HTTPMethod getMethod();

    String getPath();

}
