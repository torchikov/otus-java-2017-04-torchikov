package ru.torchikov.jdbc.datasets.base;

import javax.persistence.*;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * Base class for all entities
 */
@MappedSuperclass
public class BaseDataSet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "id")

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BaseDataSet that = (BaseDataSet) o;

		return getId() == that.getId();
	}

	@Override
	public int hashCode() {
		return (int) (getId() ^ (getId() >>> 32));
	}
}
