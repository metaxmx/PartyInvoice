package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

public class RenamePersonOp implements Operation {

	private static final long serialVersionUID = 1213998039427700139L;

	private final String oldName;

	private final String newName;

	public RenamePersonOp(String oldName, String newName) {
		super();
		this.oldName = oldName;
		this.newName = newName;
	}

	public String getOldName() {
		return oldName;
	}

	public String getNewName() {
		return newName;
	}

}
