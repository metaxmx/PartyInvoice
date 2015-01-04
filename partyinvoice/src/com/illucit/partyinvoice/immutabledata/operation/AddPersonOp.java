package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.immutabledata.Operation;

public class AddPersonOp implements Operation {

	private static final long serialVersionUID = 5226745615922182102L;

	private final String name;

	public AddPersonOp(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
