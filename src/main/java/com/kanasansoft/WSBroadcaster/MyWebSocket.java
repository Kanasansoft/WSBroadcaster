package com.kanasansoft.WSBroadcaster;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.jetty.websocket.WebSocket;

public class MyWebSocket implements WebSocket.OnTextMessage, WebSocket.OnBinaryMessage {

	private Connection connection_;
	private static Set<MyWebSocket> members_ = new CopyOnWriteArraySet<MyWebSocket>();

	@Override
	public void onOpen(Connection connection) {
		connection_ = connection;
		members_.add(this);
	}

	@Override
	public void onClose(int closeCode, String message) {
		members_.remove(this);
	}

	@Override
	public void onMessage(String data) {
		for(MyWebSocket member : members_) {
			try {
				member.connection_.sendMessage(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onMessage(byte[] data, int offset, int length) {
		for(MyWebSocket member : members_) {
			try {
				member.connection_.sendMessage(data, offset, length);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
