package io.jayms.http.light.interfaces;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.List;

public interface HTTPSessionManager {
	
	void putResponse(SocketAddress address, ByteBuffer payload);
	
	void putRequest(SocketAddress address, List<ByteBuffer> payload);

	HTTPSession getSession(SocketAddress address);
	
}
