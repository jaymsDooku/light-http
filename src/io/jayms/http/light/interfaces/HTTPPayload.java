package io.jayms.http.light.interfaces;

import java.nio.ByteBuffer;
import java.util.Map;

public interface HTTPPayload<T> {

	Map<String, Object> getHeader();
	
	T getBody();

	ByteBuffer encode();
	
}
