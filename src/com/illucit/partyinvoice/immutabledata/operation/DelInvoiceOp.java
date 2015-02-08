package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

public class DelInvoiceOp implements Operation {

	private static final long serialVersionUID = -2181924544628568479L;

	private final int id;

	public DelInvoiceOp(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
