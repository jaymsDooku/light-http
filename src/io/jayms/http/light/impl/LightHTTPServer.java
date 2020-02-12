package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LightHTTPServer implements HTTPServer {

	private int port;
	private HTTPContext context;
	private HTTPSessionManager sessionManager;
	
	private Thread serverThread;
	private LightHTTPRequestProcessor requestProcessor;
	
	public LightHTTPServer(int port) {
		this.context = new LightHTTPContext();
		this.sessionManager = new LightHTTPSessionManager();
		this.port = port;
	}
	
	@Override
	public int getPort() {
		return port;
	}
	
	@Override
	public void start() {
		requestProcessor = new LightHTTPRequestProcessor(this);
		requestProcessor.registerRequestHandler(HTTPMethod.GET, new HTTPRequestHandler() {

			@Override
			public HTTPResponse handle(HTTPRequest request) {
				String path = request.getPath();
				if (path.equals("/")) {
					Map<String, Object> header = new HashMap<>();
					header.put("Server", "jayms-light-http");
					header.put("Content-Type", "text/html");
					header.put("Date", new Date().toGMTString());

					HTTPResponse<String> response = LightHTTPResponse.builder(request.getAddress(), request.getVersion())
							.header(header)
							.body("<html><body>hello world</body></html>")
							.build();
				}
				return null;
			}

		});
		requestProcessor.start();
		serverThread = new LightHTTPServerThread(this);
		serverThread.start();
		System.out.println("Light HTTP Server has started.");
	}

	@Override
	public HTTPContext context() {
		return context;
	}
	
	@Override
	public HTTPSessionManager sessionManager() {
		return sessionManager;
	}

	@Override
	public void stop() {
		try {
			serverThread.join();
			requestProcessor.join();
			System.out.println("Light HTTP Server has stopped.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	
}
