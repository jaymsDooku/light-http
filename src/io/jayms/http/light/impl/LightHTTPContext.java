package io.jayms.http.light.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jayms.http.light.interfaces.*;
import io.jayms.http.light.interfaces.content.ContentType;

public class LightHTTPContext implements HTTPContext {

	private HTTPRequestHandler notFoundHandler;
	private Map<HTTPLocation, HTTPRequestHandler> handlers;
	
	public LightHTTPContext() {
		this.handlers = new HashMap<>();
		this.notFoundHandler = new HTTPRequestHandler() {
			@Override
			public HTTPResponse handle(HTTPRequest request) {
				Map<String, Object> header = new HashMap<>();
				header.put(HTTPHeaders.SERVER, "jayms-light-http");
				header.put(HTTPHeaders.CONTENT_TYPE, ContentType.TEXT_HTML);
				header.put(HTTPHeaders.DATE, new Date().toGMTString());

				HTTPResponse<String> response = LightHTTPResponse.builder(request.getAddress(), request.getVersion())
						.header(header)
						.statusCode(HTTPStatusCode.NOT_FOUND)
						.body("<html><body>404 not found</body></html>")
						.build();
				return response;
			}
		};
	}
	
	@Override
	public void registerHandler(HTTPLocation location, HTTPRequestHandler handler) {
		handlers.put(location, handler);
	}
	
	@Override
	public HTTPRequestHandler getHandler(HTTPLocation location) {
		if (!handlers.containsKey(location)) {
			return notFoundHandler();
		}
		return handlers.get(location);
	}

	@Override
	public HTTPRequestHandler notFoundHandler() {
		return notFoundHandler;
	}
}
