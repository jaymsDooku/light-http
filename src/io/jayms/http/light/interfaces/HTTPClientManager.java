package io.jayms.http.light.interfaces;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public interface HTTPClientManager {
	
	void putResponse(SocketAddress address, ByteBuffer payload);
	
	void putRequest(SocketAddress address, ByteBuffer payload);

	HTTPPayload getResponse(SocketAddress address);
	
	HTTPPayload getRequest(SocketAddress address);
	
}
