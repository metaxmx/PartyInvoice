package com.illucit.partyinvoice.xmldata;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.illucit.partyinvoice.data.Item;

public class XmlItem implements Item, Serializable {

	private static final long serialVersionUID = -1704495666374239967L;

	/*
	 * Attributes
	 */

	private String name;

	private long price;

	private int quantity;

	private String paidBy;

	private String personToPay;

	private String groupToPay;

	/*
	 * Attribute Getters
	 */

	@Override
	@XmlElement(name = "Name")
	public String getName() {
		return name;
	}

	@Override
	@XmlElement(name = "Price")
	@XmlJavaTypeAdapter(value = CurrencyTypeAdapter.class)
	public Long getPrice() {
		return price;
	}

	@Override
	@XmlElement(name = "Quantity")
	public int getQuantity() {
		return quantity;
	}

	@Override
	@XmlElement(name = "Total")
	@XmlJavaTypeAdapter(value = CurrencyTypeAdapter.class)
	public Long getTotal() {
		return Item.super.getTotal();
	}

	@Override
	@XmlElement(name = "PaidBy")
	public String getPaidBy() {
		return paidBy;
	}

	@Override
	@XmlElement(name = "PersonToPay")
	public String getPersonToPay() {
		return personToPay;
	}

	@Override
	@XmlElement(name = "GroupToPay")
	public String getGroupToPay() {
		return groupToPay;
	}

	/*
	 * Attribute Setters
	 */

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setTotal(Long total) {
		// NOOP
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public void setPersonToPay(String personToPay) {
		this.personToPay = personToPay;
	}

	public void setGroupToPay(String groupToPay) {
		this.groupToPay = groupToPay;
	}

}
