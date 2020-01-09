package io.jayms.http.light.interfaces;

public interface HTTPContext {

	void registerHandler(HTTPLocation location, HTTPHandler handler);
	
	HTTPHandler getHandler(HTTPLocation location);
	
}
