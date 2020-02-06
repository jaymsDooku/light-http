package io.jayms.http.light.interfaces;

public interface HTTPHandler {

	void handle(HTTPMethod method, HTTPPayload payload);
	
}
