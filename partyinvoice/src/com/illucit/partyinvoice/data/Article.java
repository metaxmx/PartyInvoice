package com.illucit.partyinvoice.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Article {

	private String name;

	private String category;

	private String comment;

	private Long singlePrice;

	private int quantity;

	private Long totalPrice;

	private Long sharePerPerson;
	
	private String paidBy;

	@XmlElement(name = "Title")
	public String getName() {
		return name;
	}

	@XmlElement(name = "Category")
	public String getCategory() {
		return category;
	}

	@XmlElement(name = "Comment")
	public String getComment() {
		return comment;
	}

	@XmlElement(name = "Price")
	@XmlJavaTypeAdapter(value = CurrencyTypeAdapter.class)
	public Long getSinglePrice() {
		return singlePrice;
	}

	@XmlElement(name = "Quantity")
	public int getQuantity() {
		return quantity;
	}

	@XmlElement(name = "Total")
	@XmlJavaTypeAdapter(value = CurrencyTypeAdapter.class)
	public Long getTotalPrice() {
		return totalPrice;
	}
	
	@XmlElement(name = "SharePerPerson")
	@XmlJavaTypeAdapter(value = CurrencyTypeAdapter.class)
	public Long getSharePerPerson() {
		return sharePerPerson;
	}

	@XmlElement(name = "PaidBy")
	public String getPaidBy() {
		return paidBy;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setSinglePrice(Long singlePrice) {
		this.singlePrice = singlePrice;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}
	
	/*
	 * Calculation
	 */
	
	public void calculate(Project project) {
		if (quantity < 0 || singlePrice == null) {
			totalPrice = null;
			sharePerPerson = null;
		} else {
			totalPrice = singlePrice * quantity;
			// TODO: Reduce persons to share (limit by group, single persons)
			sharePerPerson = totalPrice / project.getPersons().size();
		}
	}

}
