package com.illucit.partyinvoice.model;

import static com.illucit.partyinvoice.util.CurrencyUtil.currencyToString;
import static com.illucit.partyinvoice.util.Integers.nullToZero;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import com.illucit.partyinvoice.immutabledata.ImmutableItem;

/**
 * Model for and {@link ImmutableItem}.
 * 
 * @author Christian Simon
 *
 */
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

	/**
	 * Create model.
	 * 
	 * @param item
	 *            item data
	 */
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

	/**
	 * Get the property for the title.
	 * 
	 * @return title property
	 */
	public SimpleStringProperty titleProperty() {
		return titleProperty;
	}

	/**
	 * Get the property for the price (in cents).
	 * 
	 * @return price property
	 */
	public SimpleLongProperty priceProperty() {
		return priceProperty;
	}

	/**
	 * Get the property for the price (as currency String).
	 * 
	 * @return price String property
	 */
	public SimpleStringProperty priceCurrencyProperty() {
		return priceCurrencyProperty;
	}

	/**
	 * Get the property for the quantity.
	 * 
	 * @return quantity property
	 */
	public SimpleIntegerProperty quantityProperty() {
		return quantityProperty;
	}

	/**
	 * Get the property for the quantity (as String).
	 * 
	 * @return quantity String property
	 */
	public SimpleStringProperty quantityStringProperty() {
		return quantityStringProperty;
	}

	/**
	 * Get the property for the total rpice (as currency String)
	 * 
	 * @return total price String property
	 */
	public SimpleStringProperty totalProperty() {
		return totalProperty;
	}

	/**
	 * Get the property for the "paid by" person ID.
	 * 
	 * @return "paid by" person ID property
	 */
	public SimpleIntegerProperty paidbyProperty() {
		return paidbyProperty;
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
	 * Get the property for the "person to pay" person ID.
	 * 
	 * @return "person to pay" person ID property
	 */
	public SimpleIntegerProperty personToPayProperty() {
		return personToPayProperty;
	}

	/**
	 * Get the property for the "group to pay" group ID.
	 * 
	 * @return "group to pay" group ID property
	 */
	public SimpleIntegerProperty groupToPayProperty() {
		return groupToPayProperty;
	}

	/**
	 * Get the property for the "to pay model".
	 * 
	 * @return "to pay model" property
	 */
	public SimpleObjectProperty<ToPayModel> toPayModelProperty() {
		return toPayModelProperty;
	}

}
