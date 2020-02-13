package io.jayms.http.light.interfaces;

public interface HTTPContext {

	void registerHandler(HTTPLocation location, HTTPRequestHandler handler);
	
	HTTPRequestHandler getHandler(HTTPLocation location);
	
}
