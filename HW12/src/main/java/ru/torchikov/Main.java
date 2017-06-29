package ru.torchikov;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.torchikov.beans.CacheManager;
import ru.torchikov.beans.CacheManagerMBean;
import ru.torchikov.jdbc.cache.CacheEngine;
import ru.torchikov.jdbc.cache.CustomCacheEngine;
import ru.torchikov.jdbc.datasets.UserDataSet;
import ru.torchikov.jdbc.dbservice.CustomOrmDBService;
import ru.torchikov.jdbc.dbservice.DBService;
import ru.torchikov.servlets.SignInServlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import java.lang.management.ManagementFactory;

/**
 * Created by sergei on 28.06.17.
 *
 * Для упрошения при авторизации на веб странице принимает логин admin пфроль 123456
 * для коннекта к базе указать свои настройки в resources/db.properties
 */
public class Main {
    public static void main(String[] args) throws Exception {
        TestHelper.dropRableUsers();
        TestHelper.createTableUsers();
        TestHelper.addUserToDb("Mike", 21);
        TestHelper.addUserToDb("Anna", 25);
        TestHelper.addUserToDb("John", 19);

        DBService dbService = new CustomOrmDBService();
        CacheEngine<UserDataSet> cacheEngine = new CustomCacheEngine(10, 0, 0, true);
        dbService.registerCache(cacheEngine);

        CacheManager cacheManager = new CacheManager(cacheEngine);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("cacheManager:type=CustomCacheManager");
        final StandardMBean mBean = new StandardMBean(cacheManager, CacheManagerMBean.class);
        mbs.registerMBean(mBean, name);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(new SignInServlet(cacheEngine)), SignInServlet.URL);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("HW12/public_html");

        HandlerList handlerList = new HandlerList(resourceHandler, contextHandler);

        for (int i = 0; i < 10; i++) {
            dbService.getById(1L, UserDataSet.class);
            dbService.getById(2L, UserDataSet.class);
        }

        Server server = new Server(8090);
        server.setHandler(handlerList);
        server.start();
        System.out.println("Server started");
        server.join();
    }
}
