package com.illucit.partyinvoice.model;

import static com.illucit.partyinvoice.util.CurrencyUtil.currencyToString;
import javafx.beans.property.SimpleStringProperty;

import com.illucit.partyinvoice.Localization;
import com.illucit.partyinvoice.immutabledata.ImmutablePerson;

/**
 * Model for an {@link ImmutablePerson} on result view.
 * 
 * @author Christian Simon
 *
 */
public class ResultModel extends BaseModel<ImmutablePerson> {

	private final SimpleStringProperty nameProperty = new SimpleStringProperty();

	private final SimpleStringProperty transactionProperty = new SimpleStringProperty();

	private final SimpleStringProperty amountProperty = new SimpleStringProperty();

	/**
	 * Create model.
	 * 
	 * @param person
	 *            person data
	 */
	public ResultModel(ImmutablePerson person) {
		super(person);
		update(person);
	}

	@Override
	public void update(ImmutablePerson person) {
		Localization l10n = Localization.getInstance();
		nameProperty.set(person.getName());
		String transaction = (person.getDifference() == 0 ? l10n.getString("ui.result.transaction.equals") : (person
				.getDifference() > 0 ? l10n.getString("ui.result.transaction.pay") : l10n
				.getString("ui.result.transaction.get")));
		transactionProperty.set(transaction);
		amountProperty.set(person.getDifference() == 0 ? "" : currencyToString(Math.abs(person.getDifference())));
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
	 * Get property for transaction type (to pay, to get, nothing).
	 * 
	 * @return transaction type property
	 */
	public SimpleStringProperty transactionProperty() {
		return transactionProperty;
	}

	/**
	 * Get property for "to pay" or "to get" amount (as currency String).
	 * 
	 * @return mount String property
	 */
	public SimpleStringProperty amountProperty() {
		return amountProperty;
	}

	@Override
	public String toString() {
		return nameProperty().get();
	}

}
