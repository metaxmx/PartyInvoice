package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

/**
 * Operation: change person.
 * 
 * @author Christian Simon
 *
 */
public class ChangePersonOp implements Operation {

	private static final long serialVersionUID = 1213998039427700139L;

	private final int id;

	private final String newName;

	/**
	 * Create operation.
	 * 
	 * @param id
	 *            ID of existing person
	 * @param newName
	 *            new name
	 */
	public ChangePersonOp(int id, String newName) {
		this.id = id;
		this.newName = newName;
	}

	/**
	 * Get existing person ID.
	 * 
	 * @return person ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get name.
	 * 
	 * @return name
	 */
	public String getNewName() {
		return newName;
	}

}
