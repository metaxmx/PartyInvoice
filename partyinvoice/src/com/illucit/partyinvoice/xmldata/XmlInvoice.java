package com.illucit.partyinvoice.xmldata;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.illucit.partyinvoice.data.Invoice;

public class XmlInvoice implements Invoice, Serializable {

	private static final long serialVersionUID = 7835180357176500986L;

	/*
	 * Attributes
	 */

	private String title;

	private List<XmlItem> items = new LinkedList<>();

	private String paidBy;

	/*
	 * Attribute Getters
	 */

	@Override
	@XmlElement(name = "Title")
	public String getTitle() {
		return title;
	}

	@Override
	@XmlElementWrapper(name = "Items")
	@XmlElement(name = "Item")
	public List<XmlItem> getItems() {
		return items;
	}

	@Override
	@XmlElement(name = "PaidBy")
	public String getPaidBy() {
		return paidBy;
	}

	/*
	 * Attribute Setters
	 */

	public void setTitle(String title) {
		this.title = title;
	}

	public void setItems(List<XmlItem> items) {
		this.items = items;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

}
