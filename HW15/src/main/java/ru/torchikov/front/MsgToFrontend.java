package ru.torchikov.front;


import ru.torchikov.base.FrontendService;
import ru.torchikov.msgsystem.Address;
import ru.torchikov.msgsystem.Addressee;
import ru.torchikov.msgsystem.Message;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        }
    }

    public abstract void exec(FrontendService frontendService);
}