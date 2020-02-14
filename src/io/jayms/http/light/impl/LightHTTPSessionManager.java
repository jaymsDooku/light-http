package io.jayms.http.light.impl;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.jayms.http.light.interfaces.*;

public class LightHTTPSessionManager implements HTTPSessionManager {

	private HTTPPayloadParser payloadParser;
	private Map<SocketAddress, HTTPSession> sessionMap;
	
	public LightHTTPSessionManager() {
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
	public synchronized Map<SocketAddress, HTTPSession> getSessionMap() {
		return sessionMap;
	}

	@Override
	public void terminate(SocketAddress address) {
		sessionMap.remove(address);
	}
}
