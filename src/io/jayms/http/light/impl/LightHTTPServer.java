package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.*;

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
