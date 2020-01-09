package io.jayms.http.light.interfaces.util;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface SelectionKeyHandler {

	void accept(SelectionKey key) throws IOException;
	
	void write(SelectionKey key) throws IOException;
	
	void read(SelectionKey key) throws IOException;
	
}
