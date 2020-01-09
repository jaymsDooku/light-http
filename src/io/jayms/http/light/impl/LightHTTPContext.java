package io.jayms.http.light.impl;

import java.util.HashMap;
import java.util.Map;

import io.jayms.http.light.interfaces.HTTPContext;
import io.jayms.http.light.interfaces.HTTPHandler;
import io.jayms.http.light.interfaces.HTTPLocation;

public class LightHTTPContext implements HTTPContext {

	private Map<HTTPLocation, HTTPHandler> handlers;
	
	public LightHTTPContext() {
		this.handlers = new HashMap<>();
	}
	
	@Override
	public void registerHandler(HTTPLocation location, HTTPHandler handler) {
		handlers.put(location, handler);
	}
	
	@Override
	public HTTPHandler getHandler(HTTPLocation location) {
		return handlers.get(location);
	}
	
}
