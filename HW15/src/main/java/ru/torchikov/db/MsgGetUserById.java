package ru.torchikov.db;


import ru.torchikov.base.DBService;
import ru.torchikov.dataset.UserDataSet;
import ru.torchikov.front.MsgGetUserByIdAnswer;
import ru.torchikov.msgsystem.Address;
import ru.torchikov.msgsystem.MessageSystem;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
public class MsgGetUserById extends MsgToDB {
    private final MessageSystem messageSystem;
    private final long id;

    public MsgGetUserById(MessageSystem messageSystem, Address from, Address to, long id) {
        super(from, to);
        this.id = id;
        this.messageSystem = messageSystem;
    }

    @Override
    public void exec(DBService dbService) {
        UserDataSet user = dbService.getById(id);
        messageSystem.sendMessage(new MsgGetUserByIdAnswer(getTo(), getFrom(), user));
    }
}
