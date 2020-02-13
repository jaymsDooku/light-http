package io.jayms.http.light.impl;

import java.util.HashMap;
import java.util.Map;

import io.jayms.http.light.interfaces.HTTPContext;
import io.jayms.http.light.interfaces.HTTPLocation;
import io.jayms.http.light.interfaces.HTTPRequestHandler;

public class LightHTTPContext implements HTTPContext {

	private Map<HTTPLocation, HTTPRequestHandler> handlers;
	
	public LightHTTPContext() {
		this.handlers = new HashMap<>();
	}
	
	@Override
	public void registerHandler(HTTPLocation location, HTTPRequestHandler handler) {
		handlers.put(location, handler);
	}
	
	@Override
	public HTTPRequestHandler getHandler(HTTPLocation location) {
		return handlers.get(location);
	}
	
}
