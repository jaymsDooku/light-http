package io.jayms.http.light;

import io.jayms.http.light.impl.LightHTTPLocation;
import io.jayms.http.light.impl.LightHTTPServer;
import io.jayms.http.light.interfaces.HTTPHandler;
import io.jayms.http.light.interfaces.HTTPMethod;
import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPServer;

public class LightHTTPServerTest {

	public static void main(String[] args) {
		HTTPServer server = new LightHTTPServer(8080);
		server.context().registerHandler(new LightHTTPLocation("/", HTTPMethod.GET), new HTTPHandler() {

			@Override
			public void handle(HTTPMethod method, HTTPPayload payload) {
				
			}

		});

		server.start();
	}
	
}
