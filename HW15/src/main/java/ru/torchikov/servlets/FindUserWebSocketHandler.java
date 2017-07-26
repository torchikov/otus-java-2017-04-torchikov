package ru.torchikov.servlets;

import com.google.gson.Gson;
import ru.torchikov.dataset.UserDataSet;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
public class FindUserWebSocketHandler {
    private Set<FindUserWebSocket> webSockets;
    private Gson gson;

    public FindUserWebSocketHandler(Gson gson) {
        this.webSockets = ConcurrentHashMap.newKeySet();
        this.gson = gson;
    }

    public void sendMessage(UserDataSet user) {
        String message = gson.toJson(user);
        for (FindUserWebSocket socket : webSockets) {
            socket.sendString(message);
        }
    }


    public void add(FindUserWebSocket webSocket) {
        webSockets.add(webSocket);
    }


    public void remove(FindUserWebSocket webSocket) {
        webSockets.remove(webSocket);
    }

}
