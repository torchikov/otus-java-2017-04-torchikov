package ru.torchikov.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.torchikov.base.DBService;
import ru.torchikov.dataset.UserDataSet;
import ru.torchikov.msgsystem.Address;
import ru.torchikov.msgsystem.Addressee;

import java.util.function.Function;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
@SuppressWarnings("Duplicates")
public class DBServiceImpl implements DBService, Addressee {

	private final SessionFactory sessionFactory;
	private final Address address;


	public DBServiceImpl(SessionFactory sessionFactory, Address address) {
		this.sessionFactory = sessionFactory;
		this.address = address;
	}

	@Override
	public UserDataSet getById(long id) {
		return runInSession(session -> session.get(UserDataSet.class, id));
	}

	@Override
	public Long save(UserDataSet user) {
		return runInSession(session -> (Long)session.save(user));
	}

	@Override
	public void init() {

	}

	private <R> R runInSession(Function<Session, R> function) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			R result = function.apply(session);
			transaction.commit();
			return result;
		}
	}

	@Override
	public Address getAddress() {
		return address;
	}
}
