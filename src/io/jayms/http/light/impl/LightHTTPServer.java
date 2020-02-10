package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPContext;
import io.jayms.http.light.interfaces.HTTPServer;
import io.jayms.http.light.interfaces.HTTPSessionManager;

public class LightHTTPServer implements HTTPServer {

	private int port;
	private HTTPContext context;
	private HTTPSessionManager sessionManager;
	
	private Thread serverThread;
	
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
			System.out.println("Light HTTP Server has stopped.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	
}
