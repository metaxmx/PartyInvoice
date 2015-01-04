package com.illucit.partyinvoice.immutabledata;

import java.io.Serializable;

import com.illucit.partyinvoice.xmldata.CalculatedValue;

public class ImmutablePerson implements Serializable {

	private static final long serialVersionUID = 5813959340284303738L;

	/*
	 * --- Members ---
	 */
	
	private final ImmutableProject project;

	private final String name;

	@CalculatedValue
	private final Long paid;

	@CalculatedValue
	private final Long share;

	@CalculatedValue
	private final Long difference;
	
	/*
	 * --- Getters ---
	 */
	
	public ImmutableProject getProject() {
		return project;
	}

	public String getName() {
		return name;
	}

	public Long getPaid() {
		return paid;
	}

	public Long getShare() {
		return share;
	}

	public Long getDifference() {
		return difference;
	}
	
	/*
	 * --- Constructors ---
	 */

	public ImmutablePerson(ImmutableProject project, String name, Long paid, Long share, Long difference) {
		this.project = project;
		this.name = name;
		this.paid = paid;
		this.share = share;
		this.difference = difference;
	}

	/*
	 * --- Equals / Hashcode ---
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((difference == null) ? 0 : difference.hashCode());
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
		if (difference == null) {
			if (other.difference != null) {
				return false;
			}
		} else if (!difference.equals(other.difference)) {
			return false;
		}
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
