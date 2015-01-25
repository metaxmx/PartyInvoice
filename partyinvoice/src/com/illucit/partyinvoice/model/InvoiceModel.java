package com.illucit.partyinvoice.model;

import static com.illucit.partyinvoice.CurrencyUtil.currencyToString;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import com.illucit.partyinvoice.immutabledata.ImmutableInvoice;

/**
 * Model for an invoice.
 * 
 * @author Christian Simon
 *
 */
public class InvoiceModel extends BaseModel<ImmutableInvoice> {

	private final SimpleStringProperty titleProperty = new SimpleStringProperty();

	private final SimpleIntegerProperty paidByProperty = new SimpleIntegerProperty();

	private final SimpleStringProperty paidByNameProperty = new SimpleStringProperty();

	private final SimpleIntegerProperty itemsProperty = new SimpleIntegerProperty();

	private final SimpleStringProperty totalProperty = new SimpleStringProperty();

	public InvoiceModel(ImmutableInvoice invoice) {
		super(invoice);
		update(invoice);
	}

	@Override
	public void update(ImmutableInvoice invoice) {
		titleProperty.set(invoice.getTitle());
		paidByProperty.set(invoice.getPaidBy());
		paidByNameProperty.set(invoice.getPaidByPerson() == null ? "" : invoice.getPaidByPerson().getName());
		itemsProperty.set(invoice.getItems().stream().mapToInt(item -> item.getQuantity()).sum());
		totalProperty.set(currencyToString(invoice.getItems().stream().mapToLong(item -> item.getTotal()).sum()));
	}

	public SimpleStringProperty titleProperty() {
		return titleProperty;
	}

	public SimpleIntegerProperty paidByProperty() {
		return paidByProperty;
	}

	public SimpleStringProperty paidByNameProperty() {
		return paidByNameProperty;
	}

	public SimpleIntegerProperty itemsProperty() {
		return itemsProperty;
	}

	public SimpleStringProperty totalProperty() {
		return totalProperty;
	}

}
