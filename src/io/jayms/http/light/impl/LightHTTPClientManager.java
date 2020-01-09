package io.jayms.http.light.impl;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import io.jayms.http.light.interfaces.HTTPClientManager;
import io.jayms.http.light.interfaces.HTTPPayload;

public class LightHTTPClientManager implements HTTPClientManager {

	private Map<SocketAddress, HTTPPayload> requests;
	private Map<SocketAddress, HTTPPayload> responses;
	
	public LightHTTPClientManager() {
		requests = new HashMap<>();
		responses = new HashMap<>();
	}
	
	@Override
	public void putResponse(SocketAddress address, ByteBuffer payload) {
		
	}

	@Override
	public void putRequest(SocketAddress address, ByteBuffer payload) {
		
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
