package io.jayms.http.light.interfaces;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.List;

public interface HTTPPayloadParser {

	HTTPPayload parse(SocketAddress address, List<ByteBuffer> payload);
	
}
