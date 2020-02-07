package io.jayms.http.light.interfaces;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.List;

public interface HTTPClientManager {
	
	void putResponse(SocketAddress address, ByteBuffer payload);
	
	void putRequest(SocketAddress address, List<ByteBuffer> payload);

	HTTPPayload getResponse(SocketAddress address);
	
	HTTPPayload getRequest(SocketAddress address);
	
}
