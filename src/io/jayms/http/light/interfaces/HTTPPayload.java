package io.jayms.http.light.interfaces;

import io.jayms.http.light.interfaces.content.ContentType;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

public interface HTTPPayload<T> {

	SocketAddress getAddress();

	String getVersion();

	Map<String, Object> getHeader();

	ContentType getContentType();
	
	T getBody();

	ByteBuffer encode();
	
}
