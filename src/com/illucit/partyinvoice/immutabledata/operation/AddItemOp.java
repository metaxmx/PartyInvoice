package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

/**
 * Operation: add item.
 * 
 * @author Christian Simon
 *
 */
public class AddItemOp implements Operation {

	private static final long serialVersionUID = 4045780942693316042L;

	private final int invoiceId;

	private final String title;

	private final long price;

	private final int quantity;

	private final Integer paidBy;

	private final Integer personToPay;

	private final Integer groupToPay;

	/**
	 * Create operation.
	 * 
	 * @param invoiceId
	 *            ID of parent invoice
	 * @param title
	 *            new item title
	 * @param price
	 *            new item price (in cents)
	 * @param quantity
	 *            new item quantity
	 * @param paidBy
	 *            new "paid by" person ID (optional, if different from invoice
	 *            "paid by")
	 * @param personToPay
	 *            new "person to pay" person ID (optional)
	 * @param groupToPay
	 *            new "group to pay group ID (optional)
	 */
	public AddItemOp(int invoiceId, String title, long price, int quantity, Integer paidBy, Integer personToPay,
			Integer groupToPay) {
		this.invoiceId = invoiceId;
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.paidBy = paidBy;
		this.personToPay = personToPay;
		this.groupToPay = groupToPay;
	}

	/**
	 * Get ID of parent invoice.
	 * 
	 * @return invoice ID
	 */
	public int getInvoiceId() {
		return invoiceId;
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
	 * Get price (in cents).
	 * 
	 * @return price
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
