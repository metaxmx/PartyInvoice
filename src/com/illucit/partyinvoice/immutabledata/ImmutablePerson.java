package com.illucit.partyinvoice.immutabledata;

import java.io.Serializable;

import com.illucit.partyinvoice.data.Person;
import com.illucit.partyinvoice.xmldata.CalculatedValue;

/**
 * Implementation of {@link Person} in {@link ImmutableBaseData} hierarchy.
 * 
 * @author Christian Simon
 *
 */
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

	/**
	 * Get the calulcated amount of money the person has already paid.
	 * 
	 * @return already baid money in cents
	 */
	public long getPaid() {
		return paid;
	}

	/**
	 * Get the calculated share the person has to pay in total (not taking
	 * {@link #getPaid()} into account).
	 * 
	 * @return total share of the person in cents
	 */
	public long getShare() {
		return share;
	}

	/*
	 * --- Constructors ---
	 */

	/**
	 * Create immutable person.
	 * 
	 * @param id
	 *            ID opf person
	 * @param name
	 *            name of person
	 * @param paid
	 *            calculated amount paid by person
	 * @param share
	 *            calculated total share of person
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

	/**
	 * Get the difference between {@link #getShare()} and {@link #getPaid()}
	 * (positive of the person still has to pay something, negative if the
	 * person is getting something back).
	 * 
	 * @return difference in cents
	 */
	public long getDifference() {
		return getShare() - getPaid();
	}

}
