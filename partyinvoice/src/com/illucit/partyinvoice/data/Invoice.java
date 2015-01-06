package com.illucit.partyinvoice.data;

import java.util.List;

public interface Invoice {

	public String getTitle();

	public String getPaidBy();

	public List<? extends Item> getItems();

}
