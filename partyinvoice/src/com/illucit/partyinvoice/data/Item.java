package com.illucit.partyinvoice.data;

public interface Item extends BaseData {

	public String getTitle();

	public int getQuantity();

	public long getPrice();

	public default long getTotal() {
		return getQuantity() * getPrice();
	}

	// Nullable
	public Integer getPaidBy();

	// Nullable
	public Integer getPersonToPay();

	// Nullable
	public Integer getGroupToPay();

}
