package io.jayms.http.light.impl;

import java.util.Objects;

import io.jayms.http.light.interfaces.HTTPLocation;
import io.jayms.http.light.interfaces.HTTPMethod;

public class LightHTTPLocation implements HTTPLocation {

	private String path;
	private HTTPMethod method;
	
	public LightHTTPLocation(String path, HTTPMethod method) {
		this.path = path;
		this.method = method;
	}
	
	public String path() {
		return path;
	}
	
	public HTTPMethod method() {
		return method;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LightHTTPLocation)) {
			return false;
		}
		
		LightHTTPLocation loc = (LightHTTPLocation) obj;
		return loc.path.equals(this.path) && loc.method.equals(this.method);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(path, method);
	}
	
}
