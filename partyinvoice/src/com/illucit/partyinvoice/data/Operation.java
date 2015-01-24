package com.illucit.partyinvoice.data;

import java.io.Serializable;

import com.illucit.partyinvoice.immutabledata.ImmutableProjectHolder;

public interface Operation extends Serializable {
	
	public default ImmutableProjectHolder operate(ImmutableProjectHolder holder) {
		return holder.operate(this);
	}
	
}
