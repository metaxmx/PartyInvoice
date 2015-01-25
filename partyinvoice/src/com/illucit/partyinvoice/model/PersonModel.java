package com.illucit.partyinvoice.model;

import static com.illucit.partyinvoice.CurrencyUtil.currencyToString;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.StringConverter;

import com.illucit.partyinvoice.immutabledata.ImmutablePerson;

/**
 * Model for a person
 * 
 * @author Christian Simon
 *
 */
public class PersonModel extends BaseModel<ImmutablePerson> {

	private final SimpleStringProperty nameProperty = new SimpleStringProperty();

	private final SimpleStringProperty paidProperty = new SimpleStringProperty();

	private final SimpleStringProperty shareProperty = new SimpleStringProperty();

	private final SimpleStringProperty differenceProperty = new SimpleStringProperty();

	public PersonModel(ImmutablePerson person) {
		super(person);
		update(person);
	}

	@Override
	public void update(ImmutablePerson person) {
		nameProperty.set(person.getName());
		paidProperty.set(currencyToString(person.getPaid()));
		shareProperty.set(currencyToString(person.getShare()));
		differenceProperty.set(currencyToString(person.getDifference()));
	}

	public SimpleStringProperty nameProperty() {
		return nameProperty;
	}

	public SimpleStringProperty paidProperty() {
		return paidProperty;
	}

	public SimpleStringProperty shareProperty() {
		return shareProperty;
	}

	public SimpleStringProperty differenceProperty() {
		return differenceProperty;
	}

	public static final PersonStringConverter converter = new PersonStringConverter();

	public static class PersonStringConverter extends StringConverter<PersonModel> {

		@Override
		public String toString(PersonModel person) {
			return person.getId() + ": " + person.nameProperty().get();
		}

		@Override
		public PersonModel fromString(String string) {
			return null;
		}

	}

}
