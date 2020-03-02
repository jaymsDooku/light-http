package io.jayms.http.light.interfaces;

public enum HTTPStatusCode {

    OK(200),
    NOT_FOUND(404);

    private int statusCode;

    private HTTPStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int code() {
        return statusCode;
    }
}
