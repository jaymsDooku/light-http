package io.jayms.http.light.interfaces;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface HTTPSessionManager {
	
	void putRequest(SocketAddress address, List<ByteBuffer> payload);

	HTTPSession getSession(SocketAddress address);

	Map<SocketAddress, HTTPSession> getSessionMap();

	void terminate(SocketAddress address);
	
}
