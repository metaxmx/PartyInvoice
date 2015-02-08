package com.illucit.partyinvoice.data;

import java.util.List;

public interface Invoice extends BaseData {

	public String getTitle();

	public int getPaidBy();

	public List<? extends Item> getItems();

}
