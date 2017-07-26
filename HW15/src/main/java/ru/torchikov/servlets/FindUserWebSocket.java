package ru.torchikov.servlets;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.torchikov.base.FrontendService;

import java.io.IOException;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
@WebSocket
public class FindUserWebSocket {
	private Session session;
	private FrontendService frontendService;

	public FindUserWebSocket(FrontendService frontendService) {
		this.frontendService = frontendService;
	}

	@OnWebSocketConnect
	public void onOpen(Session session) {
		frontendService.getSocketHandler().add(this);
		this.session = session;
	}

	@OnWebSocketMessage
	public void onMessage(String id) {
		frontendService.handleRequest(Long.parseLong(id));
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		frontendService.getSocketHandler().remove(this);
	}

	public void sendString(String data) {
		try {
			session.getRemote().sendString(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
