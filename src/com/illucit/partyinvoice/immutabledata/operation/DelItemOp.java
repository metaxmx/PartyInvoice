package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

public class DelItemOp implements Operation {

	private static final long serialVersionUID = -6878257773523680763L;

	private final int id;

	public DelItemOp(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
