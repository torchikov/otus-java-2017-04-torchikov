package ru.torchikov.jdbc.dao;

import ru.torchikov.jdbc.ConnectionHelper;
import ru.torchikov.jdbc.Executor;
import ru.torchikov.jdbc.datasets.UserDataSet;

import java.sql.Connection;
import java.util.Optional;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * DAO for users
 */
public class UserDAO implements DAO<UserDataSet> {

	@Override
	public Optional<UserDataSet> get(long id, Class<UserDataSet> entityClass){
		try (Connection connection = ConnectionHelper.getConnection()) {
			Executor<UserDataSet> executor = new Executor<>(connection);
            String query = getLoadSqlQuery(id, entityClass);
            return executor.executeGet(query, entityClass);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean save(UserDataSet dataSet){
		try (Connection connection = ConnectionHelper.getConnection()) {
			Executor<UserDataSet> executor = new Executor<>(connection);
			String query = getSaveSqlQuery(dataSet);
			int updatedRowsCount = executor.executeUpdate(query);
			return updatedRowsCount > 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
