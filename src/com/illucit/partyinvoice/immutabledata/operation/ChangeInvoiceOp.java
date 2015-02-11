package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

/**
 * Operation: Change invoice.
 * 
 * @author Christian Simon
 *
 */
public class ChangeInvoiceOp implements Operation {

	private static final long serialVersionUID = 1213998039427700139L;

	private final int id;

	private final String newTitle;

	private final int paidBy;

	/**
	 * Create operation.
	 * 
	 * @param id
	 *            ID of existing invoice
	 * @param newTitle
	 *            new title
	 * @param paidBy
	 *            new "paid pay" person ID
	 */
	public ChangeInvoiceOp(int id, String newTitle, int paidBy) {
		this.id = id;
		this.newTitle = newTitle;
		this.paidBy = paidBy;
	}

	/**
	 * Get existing invoice ID.
	 * 
	 * @return invoice ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get new title.
	 * 
	 * @return new title
	 */
	public String getNewTitle() {
		return newTitle;
	}

	/**
	 * Get new "paid by" person ID.
	 * 
	 * @return "paid by" person ID
	 */
	public int getPaidBy() {
		return paidBy;
	}

}
