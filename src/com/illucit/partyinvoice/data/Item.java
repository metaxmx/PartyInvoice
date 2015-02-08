package com.illucit.partyinvoice.data;

/**
 * Base data: {@link Invoice} item.
 * 
 * @author Christian Simon
 *
 */
public interface Item extends BaseData {

	/**
	 * Get the title of the item
	 * 
	 * @return title
	 */
	public String getTitle();

	/**
	 * Get the quantity of the items (multiplicator for {@link #getPrice()} to
	 * calculate {@link #getTotal()}). This number must be positive.
	 * 
	 * @return quantity as int
	 */
	public int getQuantity();

	/**
	 * Get the price in the base unit (cents).
	 * 
	 * @return price
	 */
	public long getPrice();

	/**
	 * Get the total price of the item (price * quantity) in the base unit
	 * (cents).
	 * 
	 * @return total price
	 */
	public default long getTotal() {
		return getQuantity() * getPrice();
	}

	/**
	 * Get the ID of the {@link Person} who paid for this item, if it is another
	 * {@code Person} that for the {@code Invoice}.
	 * 
	 * @return ID of {@code Person} or null
	 */
	public Integer getPaidBy();

	/**
	 * Get the ID of the {@link Person} who should pay for the item alone
	 * (optional).
	 * 
	 * @return ID of {@code Person} or null
	 */
	public Integer getPersonToPay();

	/**
	 * Get the ID of the {@link Person} who should pay for the item alone
	 * (optional).
	 * 
	 * @return ID of {@code Group} or null
	 */
	public Integer getGroupToPay();

}
