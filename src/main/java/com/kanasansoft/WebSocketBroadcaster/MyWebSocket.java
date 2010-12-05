package com.kanasansoft.WebSocketBroadcaster;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.jetty.websocket.WebSocket;

public class MyWebSocket implements WebSocket {

	private Outbound outbound_;
	private static Set<MyWebSocket> members_ = new CopyOnWriteArraySet<MyWebSocket>();

	@Override
	public void onConnect(Outbound outbound) {
		outbound_ = outbound;
		members_.add(this);
	}

	@Override
	public void onDisconnect() {
		members_.remove(this);
	}

	@Override
	public void onMessage(byte frame, String data) {
		for(MyWebSocket member : members_) {
			try {
				member.outbound_.sendMessage(frame, data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onMessage(byte frame, byte[] data, int offset, int length) {
		for(MyWebSocket member : members_) {
			try {
				member.outbound_.sendMessage(frame, data, offset, length);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
