package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPClientManager;
import io.jayms.http.light.interfaces.HTTPContext;
import io.jayms.http.light.interfaces.HTTPServer;

public class LightHTTPServer implements HTTPServer {

	private int port;
	private HTTPContext context;
	private HTTPClientManager clientManager;
	
	private Thread serverThread;
	
	public LightHTTPServer(int port) {
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
	public HTTPClientManager clientManager() {
		return clientManager;
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
