package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

/**
 * Operation: add invoice.
 * 
 * @author Christian Simon
 *
 */
public class AddInvoiceOp implements Operation {

	private static final long serialVersionUID = 5226745615922182102L;

	private final String title;

	private final int paidBy;

	/**
	 * Create operation.
	 * 
	 * @param title
	 *            new invoice title
	 * @param paidBy
	 *            new "paid by" person ID
	 */
	public AddInvoiceOp(String title, int paidBy) {
		this.title = title;
		this.paidBy = paidBy;
	}

	/**
	 * Get title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Get "paid by" person ID.
	 * 
	 * @return person ID
	 */
	public int getPaidBy() {
		return paidBy;
	}

}
