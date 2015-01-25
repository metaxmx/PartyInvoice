package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

public class ChangePersonOp implements Operation {

	private static final long serialVersionUID = 1213998039427700139L;

	private final int id;

	private final String newName;

	public ChangePersonOp(int id, String newName) {
		this.id = id;
		this.newName = newName;
	}

	public int getId() {
		return id;
	}

	public String getNewName() {
		return newName;
	}

}
