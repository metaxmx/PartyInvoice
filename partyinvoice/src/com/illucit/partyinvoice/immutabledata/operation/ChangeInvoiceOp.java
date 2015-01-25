package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

public class ChangeInvoiceOp implements Operation {

	private static final long serialVersionUID = 1213998039427700139L;

	private final int id;

	private final String newTitle;

	private final int paidBy;

	public ChangeInvoiceOp(int id, String newTitle, int paidBy) {
		this.id = id;
		this.newTitle = newTitle;
		this.paidBy = paidBy;
	}

	public int getId() {
		return id;
	}

	public String getNewTitle() {
		return newTitle;
	}

	public int getPaidBy() {
		return paidBy;
	}

}
