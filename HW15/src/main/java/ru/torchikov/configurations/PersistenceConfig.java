package ru.torchikov.configurations;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.torchikov.dataset.UserDataSet;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
@Configuration
@PropertySource("classpath:/hibernate.properties")
@EnableTransactionManagement
public class PersistenceConfig {

	private final Environment environment;

	@Autowired
	public PersistenceConfig(Environment environment) {
		this.environment = environment;
	}

	@Bean
	public DataSource getDataSource() {
		String url = environment.getProperty("hibernate.connection.url");
		String login = environment.getProperty("hibernate.connection.username");
		String pass = environment.getProperty("hibernate.connection.password");
		return new DriverManagerDataSource(url, login, pass);
	}

	@Bean
	public SessionFactory getSessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(getDataSource());
		builder.addAnnotatedClasses(UserDataSet.class);
		builder.addProperties(getHibernateProperties());
		return builder.buildSessionFactory();
	}

	@Bean
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
