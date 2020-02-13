package io.jayms.http.light;

import io.jayms.http.light.impl.LightHTTPLocation;
import io.jayms.http.light.impl.LightHTTPResponse;
import io.jayms.http.light.impl.LightHTTPServer;
import io.jayms.http.light.interfaces.*;
import io.jayms.http.light.interfaces.content.ContentType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LightHTTPServerTest<T> {

	public static void main(String[] args) {
		HTTPServer server = new LightHTTPServer(8080);
		server.context().registerHandler(new LightHTTPLocation("/", HTTPMethod.GET), new HTTPRequestHandler() {

			@Override
			public HTTPResponse handle(HTTPRequest request) {
				String path = request.getLocation().path();
				if (path.equals("/")) {
					Map<String, Object> header = new HashMap<>();
					header.put(HTTPHeaders.SERVER, "jayms-light-http");
					header.put(HTTPHeaders.CONTENT_TYPE, ContentType.TEXT_HTML);
					header.put(HTTPHeaders.DATE, new Date().toGMTString());

					HTTPResponse<String> response = LightHTTPResponse.builder(request.getAddress(), request.getVersion())
							.header(header)
							.body("<html><body>hello world</body></html>")
							.build();
					return response;
				}
				return null;
			}

		});

		server.start();

		/*String s = "hello\r\n";
		String[] sp = s.split("\r\n");
		System.out.println("sp len : " + sp.length);*/
	}

}
