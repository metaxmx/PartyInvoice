package com.illucit.partyinvoice.data;

public interface Item {

	public String getName();

	public int getQuantity();

	public Long getPrice();

	public default Long getTotal() {
		Long price = getPrice();
		return getQuantity() * (price == null ? 0 : price);
	}

	// Nullable
	public String getPaidBy();

	// Nullable
	public String getPersonToPay();

	// Nullable
	public String getGroupToPay();

}
