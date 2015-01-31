package com.illucit.partyinvoice.model;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

import com.illucit.partyinvoice.data.BaseData;

public abstract class BaseModel<D extends BaseData> {

	private final int id;

	private final ReadOnlyIntegerProperty idProperty;

	public BaseModel(D data) {
		this.id = data.getId();
		this.idProperty = new ReadOnlyIntegerWrapper(id);
	}

	public abstract void update(D data);

	public int getId() {
		return id;
	}

	public ReadOnlyIntegerProperty idProperty() {
		return idProperty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BaseModel))
			return false;
		BaseModel<?> other = (BaseModel<?>) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
