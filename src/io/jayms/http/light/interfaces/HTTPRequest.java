package io.jayms.http.light.interfaces;

public interface HTTPRequest<T> extends HTTPPayload<T> {

    HTTPLocation getLocation();

}
