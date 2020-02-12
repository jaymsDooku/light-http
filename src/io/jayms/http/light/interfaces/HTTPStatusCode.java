package io.jayms.http.light.interfaces;

public enum HTTPStatusCode {

    OK(200);

    private int statusCode;

    private HTTPStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int code() {
        return statusCode;
    }
}
