package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

/**
 * Operation: delete invoice.
 * 
 * @author Christian Simon
 *
 */
public class DelInvoiceOp implements Operation {

	private static final long serialVersionUID = -2181924544628568479L;

	private final int id;

	/**
	 * Create operation.
	 * 
	 * @param id
	 *            ID of existing invoice
	 */
	public DelInvoiceOp(int id) {
		this.id = id;
	}

	/**
	 * Get existing invoice ID.
	 * 
	 * @return invoice ID
	 */
	public int getId() {
		return id;
	}

}
