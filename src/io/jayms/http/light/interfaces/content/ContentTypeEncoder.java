package io.jayms.http.light.interfaces.content;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public interface ContentTypeEncoder {

    ByteBuffer encode(Charset charset, Object data);

}
