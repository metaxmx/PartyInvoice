package com.illucit.partyinvoice.model;

import javafx.beans.property.SimpleStringProperty;

import com.illucit.partyinvoice.immutabledata.ImmutablePerson;

/**
 * Model for an {@link ImmutablePerson} to be displayed in a person dropdown
 * list.
 * 
 * @author Christian Simon
 *
 */
public class PersonListModel extends BaseModel<ImmutablePerson> {

	private final SimpleStringProperty nameProperty = new SimpleStringProperty();

	/**
	 * Create model.
	 * 
	 * @param person
	 *            person data
	 */
	public PersonListModel(ImmutablePerson person) {
		super(person);
		update(person);
	}

	@Override
	public void update(ImmutablePerson person) {
		nameProperty.set(person.getName());
	}

	/**
	 * Get the property for the person name.
	 * 
	 * @return name property
	 */
	public SimpleStringProperty nameProperty() {
		return nameProperty;
	}

	@Override
	public String toString() {
		return nameProperty().get();
	}

}
