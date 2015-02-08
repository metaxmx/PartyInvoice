package com.illucit.partyinvoice.data;

import java.util.List;

/**
 * Base data: Invoice.
 * 
 * @author Christian Simon
 *
 */
public interface Invoice extends BaseData {

	/**
	 * Get the title of the invoice.
	 * 
	 * @return title
	 */
	public String getTitle();

	/**
	 * Get the ID of the {@link Person} who paid for the invoice.
	 * 
	 * @return ID of {@code Person} who paid
	 */
	public int getPaidBy();

	/**
	 * Get the list of {@link Item}s which belong to this invoice.
	 * 
	 * @return list of {@code Item}s
	 */
	public List<? extends Item> getItems();

}
