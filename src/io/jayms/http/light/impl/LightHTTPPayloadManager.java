package io.jayms.http.light.impl;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.jayms.http.light.interfaces.*;

public class LightHTTPPayloadManager implements HTTPPayloadManager {

	private HTTPPayloadParser payloadParser;
	private Map<SocketAddress, HTTPSession> sessionMap;
	
	public LightHTTPPayloadManager() {
		payloadParser = new LightHTTPPayloadParser();
		sessionMap = new ConcurrentHashMap<>();
	}

	@Override
	public void putRequest(SocketAddress address, List<ByteBuffer> payload) {
		HTTPPayload httpPayload = payloadParser.parse(address, payload);
		HTTPSession session = getSession(address);
		session.putRequest((HTTPRequest) httpPayload);
	}

	@Override
	public HTTPSession getSession(SocketAddress address) {
		HTTPSession session = sessionMap.get(address);
		if (session == null) {
			session = new LightHTTPSession(address);
			sessionMap.put(address, session);
		}
		return session;
	}

	@Override
	public void replaceSession(SocketAddress address, HTTPSession session) {
		sessionMap.put(address, session);
	}

	@Override
	public Map<SocketAddress, HTTPSession> getSessionMap() {
		return sessionMap;
	}

	@Override
	public void terminate(SocketAddress address) {
		sessionMap.remove(address);
	}
}
