package io.jayms.http.light.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPSession;
import io.jayms.http.light.interfaces.HTTPSessionManager;
import io.jayms.http.light.interfaces.util.SelectionKeyHandler;

public class LightHTTPServerThread extends Thread {

	private LightHTTPServer httpServer;
	
	private ServerSocketChannel serverChannel;
	private Selector selector;
	private ServerSelectionKeyHandler keyHandler;
	
	public LightHTTPServerThread(LightHTTPServer httpServer) {
		this.httpServer = httpServer;
		this.keyHandler = new ServerSelectionKeyHandler();
	}
	
	@Override
	public void run() {
		try {
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.bind(new InetSocketAddress(httpServer.getPort()));
			selector = Selector.open();
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}
		
		while (true) {
			try {
				selector.select();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> readyIterator = readyKeys.iterator();
			while (readyIterator.hasNext()) {
				SelectionKey key = readyIterator.next();
				
				try {
					if (key.isAcceptable()) {
						keyHandler.accept(key);
					}
					if (key.isReadable()) {
						keyHandler.read(key);
					}
					if (key.isWritable()) {
						keyHandler.write(key);
					}
				} catch (IOException ex) {
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException cex) {}
				}

				readyIterator.remove();
			}
		}
	}
	
	public class ServerSelectionKeyHandler implements SelectionKeyHandler {
		
		ServerSelectionKeyHandler() {
		}
		
		@Override
		public void accept(SelectionKey key) throws IOException {
			ServerSocketChannel channel = (ServerSocketChannel) key.channel();
			SocketChannel client = channel.accept();
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ);
		}
		
		@Override
		public void write(SelectionKey key) throws IOException {
			SocketChannel client = (SocketChannel) key.channel();
			SocketAddress address = client.getRemoteAddress();
			HTTPSession session = httpServer.sessionManager().getSession(address);
			if (session.responses() <= 0) {
				return;
			}
			ByteBuffer current = session.getCurrentBuffer();
			if (current == null) {
				HTTPPayload payload = session.pollResponse();
				if (payload == null) {
					return;
				}
				ByteBuffer buffer = payload.encode();
				if (buffer == null) {
					return;
				}
				current = buffer;
				session.setCurrentBuffer(current);
			}
			System.out.println("current: " + Arrays.toString(current.array()));

			if (current.hasRemaining()) {
				client.write(current);
			} else {
				session.setCurrentBuffer(null);
				client.close();
			}
		}
		
		@Override
		public void read(SelectionKey key) throws IOException {
			HTTPSessionManager sessionManager = httpServer.sessionManager();
			
			SocketChannel client = (SocketChannel) key.channel();
			SocketAddress address = client.getRemoteAddress();

			List<ByteBuffer> buffers = new ArrayList<>();
			ByteBuffer buffer;
			int read = 0;
			do {
				buffer = ByteBuffer.allocate(1024 * 8);
				read = client.read(buffer);
				if (read > 0) {
					buffer.flip();
					buffers.add(buffer);
				}
			} while (read > 0);
			sessionManager.putRequest(address, buffers);
			
			key.interestOps(SelectionKey.OP_WRITE);
		}
		
	}
	
}
