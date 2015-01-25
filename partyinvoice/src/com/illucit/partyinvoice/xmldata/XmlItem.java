package com.illucit.partyinvoice.xmldata;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.illucit.partyinvoice.data.Item;

public class XmlItem implements Item, Serializable {

	private static final long serialVersionUID = -1704495666374239967L;

	/*
	 * Attributes
	 */

	private int id;

	private String title;

	private long price;

	private int quantity;

	private Integer paidBy;

	private Integer personToPay;

	private Integer groupToPay;

	/*
	 * Attribute Getters
	 */

	@Override
	@XmlAttribute(name = "id", required = true)
	public int getId() {
		return id;
	}

	@Override
	@XmlElement(name = "Title")
	public String getTitle() {
		return title;
	}

	@Override
	@XmlElement(name = "Price")
	@XmlJavaTypeAdapter(value = CurrencyTypeAdapter.class, type = long.class)
	public long getPrice() {
		return price;
	}

	@Override
	@XmlElement(name = "Quantity")
	public int getQuantity() {
		return quantity;
	}

	@Override
	@XmlElement(name = "Total")
	@XmlJavaTypeAdapter(value = CurrencyTypeAdapter.class, type = long.class)
	public long getTotal() {
		return Item.super.getTotal();
	}

	@Override
	@XmlElement(name = "PaidBy")
	public Integer getPaidBy() {
		return paidBy;
	}

	@Override
	@XmlElement(name = "PersonToPay")
	public Integer getPersonToPay() {
		return personToPay;
	}

	@Override
	@XmlElement(name = "GroupToPay")
	public Integer getGroupToPay() {
		return groupToPay;
	}

	/*
	 * Attribute Setters
	 */

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setTotal(long total) {
		// NOOP
	}

	public void setPaidBy(Integer paidBy) {
		this.paidBy = paidBy;
	}

	public void setPersonToPay(Integer personToPay) {
		this.personToPay = personToPay;
	}

	public void setGroupToPay(Integer groupToPay) {
		this.groupToPay = groupToPay;
	}

}
