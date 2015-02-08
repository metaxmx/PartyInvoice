package com.illucit.partyinvoice.data;

import java.io.Serializable;

/**
 * Interface for immutable classes to allow mutation by returning a mutated
 * instance.
 * 
 * @author Christian Simon
 *
 * @param <O>
 *            operation base type
 * @param <I>
 *            immutable base type which supports mutation
 */
public interface Mutation<O extends Serializable, I extends Mutation<O, I>> extends Serializable {

	/**
	 * Perform mutation operation and return mutated object. The original object
	 * is not changed.
	 * 
	 * @param operation
	 *            operation to perform
	 * @return mutated object
	 */
	public I mutate(O operation);

}
