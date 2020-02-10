package io.jayms.http.light.interfaces;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

public interface HTTPPayload<T> {

	SocketAddress getAddress();

	Map<String, Object> getHeader();
	
	T getBody();

	ByteBuffer encode();
	
}
