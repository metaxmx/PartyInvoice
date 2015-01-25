package com.illucit.partyinvoice.model;

import static com.illucit.partyinvoice.CurrencyUtil.currencyToString;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import com.illucit.partyinvoice.immutabledata.ImmutableItem;

public class ItemModel extends BaseModel<ImmutableItem> {

	private final SimpleStringProperty titleProperty = new SimpleStringProperty();

	private final SimpleStringProperty priceProperty = new SimpleStringProperty();

	private final SimpleIntegerProperty quantityProperty = new SimpleIntegerProperty();

	private final SimpleStringProperty totalProperty = new SimpleStringProperty();

	private final SimpleIntegerProperty paidbyProperty = new SimpleIntegerProperty();

	private final SimpleStringProperty paidByNameProperty = new SimpleStringProperty();

	private final SimpleStringProperty topayProperty = new SimpleStringProperty();

	public ItemModel(ImmutableItem item) {
		super(item);
		update(item);
	}

	@Override
	public void update(ImmutableItem item) {
		titleProperty.set(item.getTitle());
		priceProperty.set(currencyToString(item.getPrice()));
		quantityProperty.set(item.getQuantity());
		totalProperty.set(currencyToString(item.getTotal()));
		paidbyProperty.set(item.getPaidBy() == null ? 0 : item.getPaidBy());
		paidByNameProperty.set(item.getGetPaidByPerson() == null ? "" : item.getGetPaidByPerson().getName());
		topayProperty.set("ALL"); // TODO
	}

	public SimpleStringProperty titleProperty() {
		return titleProperty;
	}

	public SimpleStringProperty priceProperty() {
		return priceProperty;
	}

	public SimpleIntegerProperty quantityProperty() {
		return quantityProperty;
	}

	public SimpleStringProperty totalProperty() {
		return totalProperty;
	}

	public SimpleIntegerProperty paidbyProperty() {
		return paidbyProperty;
	}

	public SimpleStringProperty paidByNameProperty() {
		return paidByNameProperty;
	}

	public SimpleStringProperty topayProperty() {
		return topayProperty;
	}

}
