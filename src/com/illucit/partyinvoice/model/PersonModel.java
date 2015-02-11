package com.illucit.partyinvoice.model;

import static com.illucit.partyinvoice.util.CurrencyUtil.currencyToString;
import javafx.beans.property.SimpleStringProperty;

import com.illucit.partyinvoice.immutabledata.ImmutablePerson;

/**
 * Model for an {@link ImmutablePerson}.
 * 
 * @author Christian Simon
 *
 */
public class PersonModel extends BaseModel<ImmutablePerson> {

	private final SimpleStringProperty nameProperty = new SimpleStringProperty();

	private final SimpleStringProperty paidProperty = new SimpleStringProperty();

	private final SimpleStringProperty shareProperty = new SimpleStringProperty();

	private final SimpleStringProperty differenceProperty = new SimpleStringProperty();

	/**
	 * Create model.
	 * 
	 * @param person
	 *            person data
	 */
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

	/**
	 * Get property for person name.
	 * 
	 * @return name property
	 */
	public SimpleStringProperty nameProperty() {
		return nameProperty;
	}

	/**
	 * Get property for calculated "paid" (as currency String).
	 * 
	 * @return "paid" String property
	 */
	public SimpleStringProperty paidProperty() {
		return paidProperty;
	}

	/**
	 * Get property for calculated "share" (as currency String).
	 * 
	 * @return "share" String property
	 */
	public SimpleStringProperty shareProperty() {
		return shareProperty;
	}

	/**
	 * Get property for calculated "difference" (as currency String).
	 * 
	 * @return "difference" String property
	 */
	public SimpleStringProperty differenceProperty() {
		return differenceProperty;
	}

	@Override
	public String toString() {
		return nameProperty().get();
	}

}
