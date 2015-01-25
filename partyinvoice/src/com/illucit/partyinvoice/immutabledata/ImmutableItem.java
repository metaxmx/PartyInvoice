package com.illucit.partyinvoice.immutabledata;

import com.illucit.partyinvoice.data.Item;

public class ImmutableItem extends ImmutableBaseData implements Item {

	private static final long serialVersionUID = -623482843661020919L;

	/*
	 * --- Members ---
	 */

	private final String title;

	private final long price;

	private final int quantity;

	private final ImmutablePerson paidByPerson;

	private final ImmutableGroup groupToPayGroup;

	private final ImmutablePerson personToPayPerson;

	/*
	 * --- Getters ---
	 */

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public long getPrice() {
		return price;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public Integer getPaidBy() {
		return paidByPerson == null ? null : paidByPerson.getId();
	}

	public ImmutablePerson getGetPaidByPerson() {
		return paidByPerson;
	}

	@Override
	public Integer getGroupToPay() {
		return groupToPayGroup == null ? null : groupToPayGroup.getId();
	}

	public ImmutableGroup getGroupToPayGroup() {
		return groupToPayGroup;
	}

	@Override
	public Integer getPersonToPay() {
		return personToPayPerson == null ? null : personToPayPerson.getId();
	}

	public ImmutablePerson getPersonToPayPerson() {
		return personToPayPerson;
	}

	/*
	 * --- Constructors ---
	 */

	public ImmutableItem(int id, String title, long price, int quantity, ImmutablePerson paidBy,
			ImmutableGroup groupToPay, ImmutablePerson personToPay) {
		super(id);
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.paidByPerson = paidBy;
		this.groupToPayGroup = groupToPay;
		this.personToPayPerson = personToPay;
	}

}
