package io.jayms.http.light.interfaces.content.encoders;

import io.jayms.http.light.interfaces.content.ContentTypeEncoder;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TextContentTypeEncoder implements ContentTypeEncoder {

    @Override
    public ByteBuffer encode(Charset charset, Object data) {
        if (!(data instanceof String)) {
            throw new IllegalArgumentException("Text Encoder can only encode Strings.");
        }

        String str = (String) data;
        return charset.encode(str);
    }
}
