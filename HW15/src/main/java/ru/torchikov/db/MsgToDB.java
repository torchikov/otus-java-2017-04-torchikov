package ru.torchikov.db;


import ru.torchikov.base.DBService;
import ru.torchikov.msgsystem.Address;
import ru.torchikov.msgsystem.Addressee;
import ru.torchikov.msgsystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService);
}
