package ru.torchikov.jdbc.user;

import ru.torchikov.jdbc.ConnectionHelper;
import ru.torchikov.jdbc.DAO;
import ru.torchikov.jdbc.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * DAO for users
 */
public class UserDAO implements DAO<UserDataSet> {

	@Override
	public Optional<UserDataSet> get(long id, Class<UserDataSet> entityClass) throws SQLException, IllegalAccessException, InstantiationException {
		try (Connection connection = ConnectionHelper.getConnection()) {
			Executor<UserDataSet> executor = new Executor<>(connection);
            String query = getLoadSqlQuery(id, entityClass);
            return executor.executeGet(query, entityClass);
		}
	}

	@Override
	public boolean save(UserDataSet dataSet) throws SQLException {
		try (Connection connection = ConnectionHelper.getConnection()) {
			Executor<UserDataSet> executor = new Executor<>(connection);
			String query = getSaveSqlQuery(dataSet);
			int updatedRowsCount = executor.executeUpdate(query);
			return updatedRowsCount > 0;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
