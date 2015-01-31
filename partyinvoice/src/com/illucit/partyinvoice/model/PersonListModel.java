package com.illucit.partyinvoice.model;

import javafx.beans.property.SimpleStringProperty;

import com.illucit.partyinvoice.immutabledata.ImmutablePerson;

public class PersonListModel extends BaseModel<ImmutablePerson> {

	private final SimpleStringProperty nameProperty = new SimpleStringProperty();

	public PersonListModel(ImmutablePerson person) {
		super(person);
		update(person);
	}

	@Override
	public void update(ImmutablePerson person) {
		nameProperty.set(person.getName());
	}

	public SimpleStringProperty nameProperty() {
		return nameProperty;
	}

	@Override
	public String toString() {
		return nameProperty().get();
	}

}
