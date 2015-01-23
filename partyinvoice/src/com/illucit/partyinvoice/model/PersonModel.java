package com.illucit.partyinvoice.model;

import static com.illucit.partyinvoice.CurrencyUtil.currencyToString;

import com.illucit.partyinvoice.immutabledata.ImmutablePerson;

/**
 * Model for a person
 * 
 * @author Christian Simon
 *
 */
public class PersonModel {

	private String name;

	private String paid;

	private String share;

	private String difference;

	public PersonModel(ImmutablePerson person) {
		this.name = person.getName();
		this.paid = currencyToString(person.getPaid());
		this.share = currencyToString(person.getShare());
		this.difference = currencyToString(person.getDifference());
	}

	public String getName() {
		return name;
	}

	public String getPaid() {
		return paid;
	}

	public String getShare() {
		return share;
	}

	public String getDifference() {
		return difference;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

}
