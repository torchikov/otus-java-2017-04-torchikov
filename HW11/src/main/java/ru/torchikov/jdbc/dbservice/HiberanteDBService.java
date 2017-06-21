package ru.torchikov.jdbc.dbservice;

import ru.torchikov.jdbc.datasets.base.BaseDataSet;
import ru.torchikov.jdbc.datasets.base.EntityHolder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Created by sergei on 19.06.17.
 */
@SuppressWarnings("Duplicates")
public class HiberanteDBService implements DBService {
    private SessionFactory sessionFactory;

    public HiberanteDBService() {
        Configuration configuration = new Configuration();
        addDataSetsToConfiguration(configuration);
        fillProperties(configuration);
        this.sessionFactory = createSessionFactory(configuration);
    }

    @Override
    public <T extends BaseDataSet> long save(T dataSet) {
        Serializable result = runInSession(session -> session.save(dataSet));
        return (Long) result;
    }

    @Override
    public <T extends BaseDataSet> T getById(long id, Class<T> clazz) {
        return runInSession(session -> session.get(clazz, id));
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    private void addDataSetsToConfiguration(Configuration configuration) {
        EntityHolder.getEntityClasses().forEach(configuration::addAnnotatedClass);
    }

    private void fillProperties(Configuration configuration) {
        ResourceBundle rb = ResourceBundle.getBundle("hibernate");
        rb.keySet().forEach(key -> configuration.setProperty(key, rb.getString(key)));
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
