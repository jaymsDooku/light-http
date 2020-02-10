package io.jayms.http.light.interfaces;

public interface HTTPServer {

	void start();
	
	int getPort();
	
	HTTPContext context();
	
	HTTPSessionManager sessionManager();
	
	void stop();
	
}
