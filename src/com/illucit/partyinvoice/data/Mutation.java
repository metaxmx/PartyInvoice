package com.illucit.partyinvoice.data;

import java.io.Serializable;

/**
 * Interface for immutable classes to allow mutation by returning a mutated
 * instance.
 * 
 * @author Christian Simon
 *
 * @param <O>
 *            operation type
 * @param <I>
 *            immutable base type which supports mutation
 */
public interface Mutation<O extends Serializable, I extends Mutation<O, I>> extends Serializable {

	public I mutate(O operation);

}
