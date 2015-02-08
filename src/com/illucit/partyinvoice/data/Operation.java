package com.illucit.partyinvoice.data;

import java.io.Serializable;

import com.illucit.partyinvoice.immutabledata.ImmutableProjectHolder;

/**
 * Operation base class for {@link ImmutableProjectHolder}.
 * 
 * @author Christian Simon
 *
 */
public interface Operation extends Serializable {

	/**
	 * Perform operation on {@link ImmutableProjectHolder} instance.
	 * 
	 * @param holder
	 *            project holder input
	 * @return mutated project holder
	 */
	public default ImmutableProjectHolder operate(ImmutableProjectHolder holder) {
		return holder.operate(this);
	}

}
