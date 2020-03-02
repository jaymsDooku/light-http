package io.jayms.http.light.interfaces;

public enum HTTPConnection {

    CLOSED("Closed"),
    KEEP_ALIVE("Keep-Alive");

    private String toString;

    private HTTPConnection(String toString) {
        this.toString = toString;
    }

    @Override
    public String toString() {
        return toString;
    }
}
