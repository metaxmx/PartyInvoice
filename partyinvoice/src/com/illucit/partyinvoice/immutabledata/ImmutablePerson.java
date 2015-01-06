package com.illucit.partyinvoice.immutabledata;

import java.io.Serializable;

import com.illucit.partyinvoice.xmldata.CalculatedValue;

public class ImmutablePerson implements Serializable {

	private static final long serialVersionUID = 5813959340284303738L;

	/*
	 * --- Members ---
	 */

	private final String name;

	@CalculatedValue
	private final Long paid;

	@CalculatedValue
	private final Long share;

	/*
	 * --- Getters ---
	 */

	public String getName() {
		return name;
	}

	public Long getPaid() {
		return paid;
	}

	public Long getShare() {
		return share;
	}

	/*
	 * --- Constructors ---
	 */

	public ImmutablePerson(String name, Long paid, Long share) {
		this.name = name;
		this.paid = paid;
		this.share = share;
	}

	/*
	 * --- Utility Methods ---
	 */

	public long getDifference() {
		if (getPaid() == null || getShare() == null) {
			return 0l;
		}
		return getShare() - getPaid();
	}

	/*
	 * --- Equals / Hashcode ---
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((paid == null) ? 0 : paid.hashCode());
		result = prime * result + ((share == null) ? 0 : share.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ImmutablePerson)) {
			return false;
		}
		ImmutablePerson other = (ImmutablePerson) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (paid == null) {
			if (other.paid != null) {
				return false;
			}
		} else if (!paid.equals(other.paid)) {
			return false;
		}
		if (share == null) {
			if (other.share != null) {
				return false;
			}
		} else if (!share.equals(other.share)) {
			return false;
		}
		return true;
	}

}
