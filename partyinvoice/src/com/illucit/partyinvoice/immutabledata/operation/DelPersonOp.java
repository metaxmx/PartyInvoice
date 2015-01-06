package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

public class DelPersonOp implements Operation {

	private static final long serialVersionUID = -2181924544628568479L;

	private final String name;

	public DelPersonOp(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
