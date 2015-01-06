package com.illucit.partyinvoice.immutabledata;

import java.io.Serializable;

import com.illucit.partyinvoice.data.Item;

public class ImmutableItem implements Item, Serializable {

	private static final long serialVersionUID = -623482843661020919L;

	/*
	 * --- Members ---
	 */

	private final String name;

	private final long price;

	private final int quantity;

	private final String getPaidBy;

	private final String group;

	private final String person;

	/*
	 * --- Getters ---
	 */

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Long getPrice() {
		return price;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public String getPaidBy() {
		return getPaidBy;
	}

	@Override
	public String getGroupToPay() {
		return group;
	}

	@Override
	public String getPersonToPay() {
		return person;
	}

	/*
	 * --- Constructors ---
	 */

	public ImmutableItem(String name, long price, int quantity, String getPaidBy, String group, String person) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.getPaidBy = getPaidBy;
		this.group = group;
		this.person = person;
	}

}
