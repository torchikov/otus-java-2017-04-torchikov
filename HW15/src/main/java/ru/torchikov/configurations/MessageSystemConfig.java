package ru.torchikov.configurations;

import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.torchikov.base.DBService;
import ru.torchikov.base.FrontendService;
import ru.torchikov.base.MessageSystemContext;
import ru.torchikov.db.DBServiceImpl;
import ru.torchikov.front.FrontendServiceImpl;
import ru.torchikov.msgsystem.Address;
import ru.torchikov.msgsystem.MessageSystem;
import ru.torchikov.servlets.FindUserWebSocketHandler;


/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
@Configuration
@Import(PersistenceConfig.class)
public class MessageSystemConfig {

    @Bean
    public MessageSystem getMessageSystem() {
        return new MessageSystem();
    }

    @Bean
    public MessageSystemContext getMessageSystemContext(MessageSystem messageSystem) {
        return new MessageSystemContext(messageSystem);
    }

    @Bean
    public FrontendService getFrontendService(MessageSystemContext messageContext,
                                              MessageSystem messageSystem,
                                              FindUserWebSocketHandler socketHandler) {
        Address frontendAddress = new Address("Frontend");
        FrontendServiceImpl frontendService = new FrontendServiceImpl(messageContext, frontendAddress, socketHandler);
        messageContext.setFrontAddress(frontendAddress);
        messageSystem.addAddressee(frontendService);
        return frontendService;
    }

    @Bean
    public DBService getDBService(SessionFactory sessionFactory, MessageSystem messageSystem,
                                  MessageSystemContext messageSystemContext) {
        Address dbAddress = new Address("DB");
        DBServiceImpl dbService = new DBServiceImpl(sessionFactory, dbAddress);
        messageSystemContext.setDbAddress(dbAddress);
        messageSystem.addAddressee(dbService);
        return dbService;
    }

    @Bean
    public FindUserWebSocketHandler getFindUserWebSocketHandler() {
        return new FindUserWebSocketHandler(new Gson());
    }
}
