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

}
