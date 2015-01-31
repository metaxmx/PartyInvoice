package com.illucit.partyinvoice.model;

import static com.illucit.partyinvoice.util.CurrencyUtil.currencyToString;
import static com.illucit.partyinvoice.util.Integers.nullToZero;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import com.illucit.partyinvoice.immutabledata.ImmutableItem;

public class ItemModel extends BaseModel<ImmutableItem> {

	private final SimpleStringProperty titleProperty = new SimpleStringProperty();

	private final SimpleLongProperty priceProperty = new SimpleLongProperty();

	private final SimpleStringProperty priceCurrencyProperty = new SimpleStringProperty();

	private final SimpleIntegerProperty quantityProperty = new SimpleIntegerProperty();

	private final SimpleStringProperty quantityStringProperty = new SimpleStringProperty();

	private final SimpleStringProperty totalProperty = new SimpleStringProperty();

	private final SimpleIntegerProperty paidbyProperty = new SimpleIntegerProperty();

	private final SimpleStringProperty paidByNameProperty = new SimpleStringProperty();

	private final SimpleObjectProperty<PersonListModel> paidByModelProperty = new SimpleObjectProperty<>();

	private final SimpleIntegerProperty personToPayProperty = new SimpleIntegerProperty();

	private final SimpleIntegerProperty groupToPayProperty = new SimpleIntegerProperty();

	private final SimpleObjectProperty<ToPayModel> toPayModelProperty = new SimpleObjectProperty<>();

	public ItemModel(ImmutableItem item) {
		super(item);
		update(item);
	}

	@Override
	public void update(ImmutableItem item) {
		titleProperty.set(item.getTitle());
		priceProperty.set(item.getPrice());
		priceCurrencyProperty.set(currencyToString(item.getPrice()));
		quantityProperty.set(item.getQuantity());
		quantityStringProperty.set("" + item.getQuantity());
		totalProperty.set(currencyToString(item.getTotal()));
		paidbyProperty.set(item.getPaidBy() == null ? 0 : item.getPaidBy());
		paidByNameProperty.set(item.getGetPaidByPerson() == null ? "" : item.getGetPaidByPerson().getName());
		paidByModelProperty.set(item.getGetPaidByPerson() == null ? null : new PersonListModel(item
				.getGetPaidByPerson()));
		personToPayProperty.set(nullToZero(item.getPersonToPay()));
		groupToPayProperty.set(nullToZero(item.getGroupToPay()));

		ToPayModel toPay = new ToPayModel();
		if (item.getPersonToPayPerson() != null) {
			toPay = new ToPayModel(item.getPersonToPayPerson());
		} else if (item.getGroupToPayGroup() != null) {
			toPay = new ToPayModel(item.getGroupToPayGroup());
		}
		toPayModelProperty.set(toPay);
	}

	public SimpleStringProperty titleProperty() {
		return titleProperty;
	}

	public SimpleLongProperty priceProperty() {
		return priceProperty;
	}

	public SimpleStringProperty priceCurrencyProperty() {
		return priceCurrencyProperty;
	}

	public SimpleIntegerProperty quantityProperty() {
		return quantityProperty;
	}

	public SimpleStringProperty quantityStringProperty() {
		return quantityStringProperty;
	}

	public SimpleStringProperty totalProperty() {
		return totalProperty;
	}

	public SimpleIntegerProperty paidbyProperty() {
		return paidbyProperty;
	}

	public SimpleIntegerProperty personToPayProperty() {
		return personToPayProperty;
	}

	public SimpleIntegerProperty groupToPayProperty() {
		return groupToPayProperty;
	}

	public SimpleStringProperty paidByNameProperty() {
		return paidByNameProperty;
	}

	public SimpleObjectProperty<PersonListModel> paidByModelProperty() {
		return paidByModelProperty;
	}

	public SimpleObjectProperty<ToPayModel> toPayModelProperty() {
		return toPayModelProperty;
	}

}
