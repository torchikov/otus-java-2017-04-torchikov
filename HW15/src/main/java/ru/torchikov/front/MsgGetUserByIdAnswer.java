package ru.torchikov.front;


import ru.torchikov.base.FrontendService;
import ru.torchikov.dataset.UserDataSet;
import ru.torchikov.msgsystem.Address;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
public class MsgGetUserByIdAnswer extends MsgToFrontend {
    private UserDataSet user;

    public MsgGetUserByIdAnswer(Address from, Address to, UserDataSet user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(user);
    }
}
