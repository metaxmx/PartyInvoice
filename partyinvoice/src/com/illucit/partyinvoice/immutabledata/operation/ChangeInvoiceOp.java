package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

public class ChangeInvoiceOp implements Operation {

	private static final long serialVersionUID = 1213998039427700139L;

	private final String oldTitle;

	private final String newTitle;

	private final String paidBy;

	public ChangeInvoiceOp(String oldTitle, String newTitle, String paidBy) {
		super();
		this.oldTitle = oldTitle;
		this.newTitle = newTitle;
		this.paidBy = paidBy;
	}

	public String getOldTitle() {
		return oldTitle;
	}

	public String getNewTitle() {
		return newTitle;
	}

	public String getPaidBy() {
		return paidBy;
	}

}
