package io.jayms.http.light.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import io.jayms.http.light.interfaces.HTTPClientManager;
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
				readyIterator.remove();
				
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
			
			client.register(selector, SelectionKey.OP_READ);
		}
		
		@Override
		public void write(SelectionKey key) throws IOException {
			SocketChannel client = (SocketChannel) key.channel();
			ByteBuffer buffer = (ByteBuffer) key.attachment();
			if (buffer.hasRemaining()) {
				client.write(buffer);
			} else {
				client.close();
			}
		}
		
		@Override
		public void read(SelectionKey key) throws IOException {
			HTTPClientManager clientManager = httpServer.clientManager();
			
			SocketChannel client = (SocketChannel) key.channel();
			SocketAddress address = client.getRemoteAddress();
			
			ByteBuffer buffer = (ByteBuffer) key.attachment();
			client.read(buffer);
			clientManager.putRequest(address, buffer);
			
			key.interestOps(SelectionKey.OP_WRITE);
			key.attach(httpServer.clientManager().getResponse(address));
		}
		
	}
	
}
