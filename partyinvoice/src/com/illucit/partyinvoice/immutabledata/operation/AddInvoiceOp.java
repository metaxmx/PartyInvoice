package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

public class AddInvoiceOp implements Operation {

	private static final long serialVersionUID = 5226745615922182102L;

	private final String title;

	private final String paidBy;

	public AddInvoiceOp(String title, String paidBy) {
		this.title = title;
		this.paidBy = paidBy;
	}

	public String getTitle() {
		return title;
	}

	public String getPaidBy() {
		return paidBy;
	}

}
