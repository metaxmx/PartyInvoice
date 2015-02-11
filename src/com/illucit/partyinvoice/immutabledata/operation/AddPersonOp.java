package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

/**
 * Operation: add person.
 * 
 * @author Christian Simon
 *
 */
public class AddPersonOp implements Operation {

	private static final long serialVersionUID = 5226745615922182102L;

	private final String name;

	/**
	 * Create operation.
	 * 
	 * @param name
	 *            new person name
	 */
	public AddPersonOp(String name) {
		this.name = name;
	}

	/**
	 * Get person name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

}
