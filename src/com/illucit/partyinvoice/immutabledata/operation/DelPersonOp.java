package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

public class DelPersonOp implements Operation {

	private static final long serialVersionUID = -2181924544628568479L;

	private final int id;

	public DelPersonOp(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
