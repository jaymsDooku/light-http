package io.jayms.http.light.interfaces;

import java.nio.ByteBuffer;
import java.util.List;

public interface HTTPPayloadParser {

	HTTPPayload parse(List<ByteBuffer> payload);
	
}
