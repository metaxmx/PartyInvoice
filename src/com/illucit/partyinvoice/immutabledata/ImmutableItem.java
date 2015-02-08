package com.illucit.partyinvoice.immutabledata;

import com.illucit.partyinvoice.data.Item;

/**
 * Implementation of {@link Item} in {@link ImmutableBaseData} hierarchy.
 * 
 * @author Christian Simon
 *
 */
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

	/**
	 * Get the "paid by" person object.
	 * 
	 * @return person object or null
	 */
	public ImmutablePerson getGetPaidByPerson() {
		return paidByPerson;
	}

	@Override
	public Integer getGroupToPay() {
		return groupToPayGroup == null ? null : groupToPayGroup.getId();
	}

	/**
	 * Get the "group to pay" group object.
	 * 
	 * @return group object or null
	 */
	public ImmutableGroup getGroupToPayGroup() {
		return groupToPayGroup;
	}

	@Override
	public Integer getPersonToPay() {
		return personToPayPerson == null ? null : personToPayPerson.getId();
	}

	/**
	 * Get the "person to pay" person object.
	 * 
	 * @return person object or null
	 */
	public ImmutablePerson getPersonToPayPerson() {
		return personToPayPerson;
	}

	/*
	 * --- Constructors ---
	 */

	/**
	 * Create immutable item.
	 * 
	 * @param id
	 *            ID of item
	 * @param title
	 *            title of item
	 * @param price
	 *            price of item
	 * @param quantity
	 *            quanitity of item
	 * @param paidBy
	 *            "paid by" person object (optional)
	 * @param groupToPay
	 *            "group to pay" group object (optional)
	 * @param personToPay
	 *            "person to pay" person object (optional)
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
