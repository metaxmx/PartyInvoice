package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

/**
 * Operation: delete person.
 * 
 * @author Christian Simon
 *
 */
public class DelPersonOp implements Operation {

	private static final long serialVersionUID = -2181924544628568479L;

	private final int id;

	/**
	 * Create operation.
	 * 
	 * @param id
	 *            ID of existing person
	 */
	public DelPersonOp(int id) {
		this.id = id;
	}

	/**
	 * Get existing person ID.
	 * 
	 * @return person ID
	 */
	public int getId() {
		return id;
	}

}
