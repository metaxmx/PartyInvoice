package com.illucit.partyinvoice.immutabledata.operation;

import com.illucit.partyinvoice.data.Operation;

public class ChangeItemOp implements Operation {

	private static final long serialVersionUID = 4045780942693316042L;

	private final int id;

	private final String title;

	private final long price;

	private final int quantity;

	private final Integer paidBy;

	private final Integer personToPay;

	private final Integer groupToPay;

	public ChangeItemOp(int id, String title, long price, int quantity, Integer paidBy, Integer personToPay,
			Integer groupToPay) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.paidBy = paidBy;
		this.personToPay = personToPay;
		this.groupToPay = groupToPay;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public long getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public Integer getPaidBy() {
		return paidBy;
	}

	public Integer getPersonToPay() {
		return personToPay;
	}

	public Integer getGroupToPay() {
		return groupToPay;
	}

}
