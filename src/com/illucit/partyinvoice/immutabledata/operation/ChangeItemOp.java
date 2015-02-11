package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

/**
 * Operation: change item.
 * 
 * @author Christian Simon
 *
 */
public class ChangeItemOp implements Operation {

	private static final long serialVersionUID = 4045780942693316042L;

	private final int id;

	private final String title;

	private final long price;

	private final int quantity;

	private final Integer paidBy;

	private final Integer personToPay;

	private final Integer groupToPay;

	/**
	 * Create operation.
	 * 
	 * @param id
	 *            ID of existing item
	 * @param title
	 *            new title
	 * @param price
	 *            new price (in cents)
	 * @param quantity
	 *            new quantity
	 * @param paidBy
	 *            new "paid by" person ID (optional)
	 * @param personToPay
	 *            new "person to pay" person ID (optional)
	 * @param groupToPay
	 *            new "group to pay" group ID (optional)
	 */
	public ChangeItemOp(int id, String title, long price, int quantity, Integer paidBy, Integer personToPay,
			Integer groupToPay) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.paidBy = paidBy;
		this.personToPay = personToPay;
		this.groupToPay = groupToPay;
	}

	/**
	 * Get existing item ID.
	 * 
	 * @return item ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Get price.
	 * 
	 * @return price in cents
	 */
	public long getPrice() {
		return price;
	}

	/**
	 * Get quantity.
	 * 
	 * @return quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Get "paid by" person ID.
	 * 
	 * @return person ID or null
	 */
	public Integer getPaidBy() {
		return paidBy;
	}

	/**
	 * Get "person to pay" person ID.
	 * 
	 * @return person ID or null
	 */
	public Integer getPersonToPay() {
		return personToPay;
	}

	/**
	 * Get "group to pay" group ID.
	 * 
	 * @return group ID or null
	 */
	public Integer getGroupToPay() {
		return groupToPay;
	}

}
