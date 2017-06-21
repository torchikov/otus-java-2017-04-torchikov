package ru.torchikov.jdbc.datasets;

import ru.torchikov.jdbc.datasets.base.BaseDataSet;

import javax.persistence.*;
import java.util.List;

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

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

	private AddressDataSet address;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private List<PhoneDataSet> phones;

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

	public AddressDataSet getAddress() {
		return address;
	}

	public void setAddress(AddressDataSet address) {
		this.address = address;
	}

	public List<PhoneDataSet> getPhones() {
		return phones;
	}

	public void setPhones(List<PhoneDataSet> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return "UserDataSet{" +
				"name='" + name + '\'' +
				", age=" + age +
				", address=" + address +
				", phones=" + phones +
				'}';
	}
}
