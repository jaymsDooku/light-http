package io.jayms.http.light.interfaces.content;

import io.jayms.http.light.interfaces.content.encoders.TextContentTypeEncoder;

import java.nio.ByteBuffer;

public enum ContentType {

    /*TEXT_PLAIN("text/plain"),
    TEXT_CSS("text/css"),
    TEXT_XML("text/xml"),*/
    TEXT_HTML("text/html", new TextContentTypeEncoder());

    private String text;
    private ContentTypeEncoder encoder;

    private ContentType(String text, ContentTypeEncoder encoder) {
        this.text = text;
        this.encoder = encoder;
    }

    public String getText() {
        return text;
    }

    public ContentTypeEncoder getEncoder() {
        return encoder;
    }

}
