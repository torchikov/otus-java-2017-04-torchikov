package ru.torchikov.jdbc.datasets.user;

import org.hibernate.annotations.Fetch;
import ru.torchikov.jdbc.datasets.BaseDataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * User entity
 */
@Entity
@Table(name = "users")
public class UserDataSet extends BaseDataSet {

	@Column(name = "name")
	private String name;

	@Column(name = "age", nullable = false)
	private int age;

	public UserDataSet() {
	}

	public UserDataSet(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
