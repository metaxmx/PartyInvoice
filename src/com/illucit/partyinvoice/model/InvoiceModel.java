package com.illucit.partyinvoice.model;

import static com.illucit.partyinvoice.util.CurrencyUtil.currencyToString;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import com.illucit.partyinvoice.immutabledata.ImmutableInvoice;

/**
 * Model for an {@link ImmutableInvoice}.
 * 
 * @author Christian Simon
 *
 */
public class InvoiceModel extends BaseModel<ImmutableInvoice> {

	private final SimpleStringProperty titleProperty = new SimpleStringProperty();

	private final SimpleIntegerProperty paidByProperty = new SimpleIntegerProperty();

	private final SimpleStringProperty paidByNameProperty = new SimpleStringProperty();

	private final SimpleObjectProperty<PersonListModel> paidByModelProperty = new SimpleObjectProperty<>();

	private final SimpleIntegerProperty itemsProperty = new SimpleIntegerProperty();

	private final SimpleStringProperty totalProperty = new SimpleStringProperty();

	/**
	 * Create model.
	 * 
	 * @param invoice
	 *            invoice data
	 */
	public InvoiceModel(ImmutableInvoice invoice) {
		super(invoice);
		update(invoice);
	}

	@Override
	public void update(ImmutableInvoice invoice) {
		titleProperty.set(invoice.getTitle());
		paidByProperty.set(invoice.getPaidBy());
		paidByNameProperty.set(invoice.getPaidByPerson() == null ? "" : invoice.getPaidByPerson().getName());
		paidByModelProperty.set(new PersonListModel(invoice.getPaidByPerson()));
		itemsProperty.set(invoice.getItems().stream().mapToInt(item -> item.getQuantity()).sum());
		totalProperty.set(currencyToString(invoice.getItems().stream().mapToLong(item -> item.getTotal()).sum()));
	}

	/**
	 * Get the property for the title.
	 * 
	 * @return title property
	 */
	public SimpleStringProperty titleProperty() {
		return titleProperty;
	}

	/**
	 * Get the property for the "paid by" person ID.
	 * 
	 * @return "paid by" person ID property
	 */
	public SimpleIntegerProperty paidByProperty() {
		return paidByProperty;
	}

	/**
	 * Get the property for the "paid by" person name.
	 * 
	 * @return "paid by" person name property
	 */
	public SimpleStringProperty paidByNameProperty() {
		return paidByNameProperty;
	}

	/**
	 * Get the property for the "paid by" person model.
	 * 
	 * @return "paid by" person model property
	 */
	public SimpleObjectProperty<PersonListModel> paidByModelProperty() {
		return paidByModelProperty;
	}

	/**
	 * Get the property for the total number of items (sum of all quantities of
	 * all the items).
	 * 
	 * @return item number property
	 */
	public SimpleIntegerProperty itemsProperty() {
		return itemsProperty;
	}

	/**
	 * Get the property for the total price (as currency String)
	 * 
	 * @return total price property
	 */
	public SimpleStringProperty totalProperty() {
		return totalProperty;
	}

}
