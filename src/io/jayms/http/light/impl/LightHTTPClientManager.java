package io.jayms.http.light.impl;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jayms.http.light.interfaces.HTTPClientManager;
import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPPayloadParser;

public class LightHTTPClientManager implements HTTPClientManager {

	private HTTPPayloadParser payloadParser;
	private Map<SocketAddress, HTTPPayload> requests;
	private Map<SocketAddress, HTTPPayload> responses;
	
	public LightHTTPClientManager() {
		payloadParser = new LightHTTPPayloadParser();
		requests = new HashMap<>();
		responses = new HashMap<>();
	}
	
	@Override
	public void putResponse(SocketAddress address, ByteBuffer payload) {

	}

	@Override
	public void putRequest(SocketAddress address, List<ByteBuffer> payload) {
		HTTPPayload httpPayload = payloadParser.parse(payload);
		requests.put(address, httpPayload);
	}

	@Override
	public HTTPPayload getResponse(SocketAddress address) {
		return null;
	}

	@Override
	public HTTPPayload getRequest(SocketAddress address) {
		return null;
	}

	
	
}
