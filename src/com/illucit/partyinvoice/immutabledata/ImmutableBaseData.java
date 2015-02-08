package com.illucit.partyinvoice.immutabledata;

import java.io.Serializable;

import com.illucit.partyinvoice.data.BaseData;

/**
 * Abstract implementation of {@link BaseData}.
 * 
 * @author Christian Simon
 *
 */
public abstract class ImmutableBaseData implements BaseData, Serializable {

	private static final long serialVersionUID = -3933667836082399386L;

	/*
	 * --- Members ---
	 */

	private final int id;

	/*
	 * --- Getters ---
	 */

	@Override
	public int getId() {
		return id;
	}

	/*
	 * --- Constructors ---
	 */

	/**
	 * Create base data
	 * 
	 * @param id
	 *            ID of data
	 */
	public ImmutableBaseData(int id) {
		this.id = id;
	}

	/*
	 * --- Equals / Hashcode ---
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ImmutableBaseData))
			return false;
		ImmutableBaseData other = (ImmutableBaseData) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
