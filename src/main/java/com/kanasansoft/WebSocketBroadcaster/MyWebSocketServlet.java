package com.kanasansoft.WebSocketBroadcaster;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class MyWebSocketServlet extends WebSocketServlet {

	@Override
	protected WebSocket doWebSocketConnect(HttpServletRequest request,
			String protocol) {
		return new MyWebSocket();
	}

}
