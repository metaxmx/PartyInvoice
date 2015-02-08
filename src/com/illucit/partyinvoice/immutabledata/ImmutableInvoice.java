package com.illucit.partyinvoice.immutabledata;

import java.util.List;

import com.illucit.partyinvoice.data.Invoice;

/**
 * Implementation of {@link Invoice} in {@link ImmutableBaseData} hierarchy.
 * 
 * @author Christian Simon
 *
 */
public class ImmutableInvoice extends ImmutableBaseData implements Invoice {

	private static final long serialVersionUID = 8627028055563208694L;

	/*
	 * --- Members ---
	 */

	private final String title;

	private final ImmutablePerson paidByPerson;

	private final List<ImmutableItem> items;

	/*
	 * --- Getters ---
	 */

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public int getPaidBy() {
		return paidByPerson.getId();
	}

	@Override
	public List<ImmutableItem> getItems() {
		return items;
	}

	/**
	 * Get the "paid by" {@link ImmutablePerson} object.
	 * 
	 * @return person object
	 */
	public ImmutablePerson getPaidByPerson() {
		return paidByPerson;
	}

	/*
	 * --- Constructors ---
	 */

	/**
	 * Create immutable invoice.
	 * 
	 * @param id
	 *            ID of invoice
	 * @param title
	 *            title of invoice
	 * @param paidByPerson
	 *            "paid by" person object
	 * @param items
	 *            list of {@link ImmutableItem}s
	 */
	public ImmutableInvoice(int id, String title, ImmutablePerson paidByPerson, List<ImmutableItem> items) {
		super(id);
		this.title = title;
		this.paidByPerson = paidByPerson;
		this.items = items;
	}

}
