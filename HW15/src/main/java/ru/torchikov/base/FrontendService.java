package ru.torchikov.base;

import ru.torchikov.dataset.UserDataSet;
import ru.torchikov.servlets.FindUserWebSocketHandler;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
public interface FrontendService {
    void handleRequest(long id);

    void addUser(UserDataSet user);

    FindUserWebSocketHandler getSocketHandler();

}

