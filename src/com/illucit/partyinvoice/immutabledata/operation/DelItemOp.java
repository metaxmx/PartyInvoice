package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

/**
 * Operation: delete item.
 * 
 * @author Christian Simon
 *
 */
public class DelItemOp implements Operation {

	private static final long serialVersionUID = -6878257773523680763L;

	private final int id;

	/**
	 * Create operation.
	 * 
	 * @param id
	 *            ID of existing item
	 */
	public DelItemOp(int id) {
		this.id = id;
	}

	/**
	 * Get existing item ID.
	 * 
	 * @return item ID
	 */
	public int getId() {
		return id;
	}

}
