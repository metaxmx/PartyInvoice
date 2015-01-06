package com.illucit.partyinvoice.immutabledata;

import java.io.Serializable;
import java.util.List;

import com.illucit.partyinvoice.data.Invoice;

public class ImmutableInvoice implements Invoice, Serializable {

	private static final long serialVersionUID = 8627028055563208694L;

	/*
	 * --- Members ---
	 */

	private final String title;

	private final String paidBy;

	private final List<ImmutableItem> items;

	/*
	 * --- Getters ---
	 */

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getPaidBy() {
		return paidBy;
	}

	@Override
	public List<ImmutableItem> getItems() {
		return items;
	}

	/*
	 * --- Constructors ---
	 */

	public ImmutableInvoice(String title, String paidBy, List<ImmutableItem> items) {
		this.title = title;
		this.paidBy = paidBy;
		this.items = items;
	}

}
