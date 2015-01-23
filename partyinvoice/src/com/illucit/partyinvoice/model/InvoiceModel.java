package com.illucit.partyinvoice.model;

import static com.illucit.partyinvoice.CurrencyUtil.currencyToString;

import com.illucit.partyinvoice.immutabledata.ImmutableInvoice;

/**
 * Model for an invoice.
 * 
 * @author Christian Simon
 *
 */
public class InvoiceModel {

	private String title;

	private String paidBy;

	private Integer items;

	private String total;

	public InvoiceModel(ImmutableInvoice invoice) {
		this.title = invoice.getTitle();
		this.paidBy = invoice.getPaidBy();
		this.items = invoice.getItems().size();
		this.total = currencyToString(invoice.getItems().stream().mapToLong(item -> item.getTotal()).sum());
	}

	public String getTitle() {
		return title;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public Integer getItems() {
		return items;
	}

	public String getTotal() {
		return total;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public void setItems(Integer items) {
		this.items = items;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
