package ru.torchikov.front;


import ru.torchikov.base.FrontendService;
import ru.torchikov.base.MessageSystemContext;
import ru.torchikov.dataset.UserDataSet;
import ru.torchikov.db.MsgGetUserById;
import ru.torchikov.msgsystem.Address;
import ru.torchikov.msgsystem.Addressee;
import ru.torchikov.msgsystem.Message;
import ru.torchikov.servlets.FindUserWebSocketHandler;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
public class FrontendServiceImpl implements FrontendService, Addressee {
    private final Address address;
    private final MessageSystemContext context;
    private final FindUserWebSocketHandler socketHandler;

    public FrontendServiceImpl(MessageSystemContext context, Address address, FindUserWebSocketHandler socketHandler) {
        this.context = context;
        this.address = address;
        this.socketHandler = socketHandler;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void handleRequest(long id) {
        Message message = new MsgGetUserById(context.getMessageSystem(), getAddress(), context.getDbAddress(), id);
        context.getMessageSystem().sendMessage(message);
    }

    public void addUser(UserDataSet user) {
        socketHandler.sendMessage(user);
    }

    @Override
    public FindUserWebSocketHandler getSocketHandler() {
        return socketHandler;
    }
}
