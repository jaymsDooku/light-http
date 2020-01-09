package io.jayms.http.light.interfaces;

import java.nio.ByteBuffer;

public interface HTTPPayloadParser {

	HTTPPayload parse(ByteBuffer buffer);
	
}
