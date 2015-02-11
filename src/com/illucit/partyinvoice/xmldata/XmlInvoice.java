package com.illucit.partyinvoice.xmldata;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.illucit.partyinvoice.data.Invoice;

/**
 * Implementation of {@link Invoice} for XML serialization.
 * 
 * @author Christian Simon
 *
 */
@SuppressWarnings("javadoc")
public class XmlInvoice implements Invoice, Serializable {

	private static final long serialVersionUID = 7835180357176500986L;

	/*
	 * Attributes
	 */

	private int id;

	private String title;

	private List<XmlItem> items = new LinkedList<>();

	private int paidBy;

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
	@XmlElementWrapper(name = "Items")
	@XmlElement(name = "Item")
	public List<XmlItem> getItems() {
		return items;
	}

	@Override
	@XmlElement(name = "PaidBy")
	public int getPaidBy() {
		return paidBy;
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

	public void setItems(List<XmlItem> items) {
		this.items = items;
	}

	public void setPaidBy(int paidBy) {
		this.paidBy = paidBy;
	}

}
