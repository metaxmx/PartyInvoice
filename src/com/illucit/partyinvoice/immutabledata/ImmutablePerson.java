package com.illucit.partyinvoice.immutabledata;

import java.io.Serializable;

import com.illucit.partyinvoice.data.Person;
import com.illucit.partyinvoice.xmldata.CalculatedValue;

public class ImmutablePerson extends ImmutableBaseData implements Person, Serializable {

	private static final long serialVersionUID = 5813959340284303738L;

	/*
	 * --- Members ---
	 */

	private final String name;

	@CalculatedValue
	private final long paid;

	@CalculatedValue
	private final long share;

	/*
	 * --- Getters ---
	 */

	@Override
	public String getName() {
		return name;
	}

	public long getPaid() {
		return paid;
	}

	public long getShare() {
		return share;
	}

	/*
	 * --- Constructors ---
	 */

	public ImmutablePerson(int id, String name, long paid, long share) {
		super(id);
		this.name = name;
		this.paid = paid;
		this.share = share;
	}

	/*
	 * --- Utility Methods ---
	 */

	public long getDifference() {
		return getShare() - getPaid();
	}

}
