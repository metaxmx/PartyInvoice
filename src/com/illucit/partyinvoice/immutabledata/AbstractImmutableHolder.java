package com.illucit.partyinvoice.immutabledata;

import java.io.Serializable;
import java.util.function.Supplier;

import com.illucit.partyinvoice.data.Mutation;

/**
 * Abstract implementation of a holder of an immutable object type, which
 * supports mutations and undo/redo steps.
 * 
 * @author Christian Simon
 *
 * @param <O>
 *            operation base class for {@link Mutation}
 * @param <I>
 *            immutable data type (extends {@link Mutation})
 * @param <H>
 *            {@link AbstractImmutableHolder} implementation class
 */
public abstract class AbstractImmutableHolder<O extends Serializable, I extends Mutation<O, I>, H extends AbstractImmutableHolder<O, I, H>>
		implements Serializable {

	private static final long serialVersionUID = 1026697055364277344L;

	/*
	 * --- Members ---
	 */

	protected final I value;

	protected final H undoStep;

	protected final H redoStep;

	/*
	 * --- Getters ---
	 */

	protected I getValue() {
		return value;
	}

	/*
	 * --- Constructors ---
	 */

	/**
	 * Create emtpy holder.
	 */
	public AbstractImmutableHolder() {
		this.value = createEmpty();
		this.undoStep = null;
		this.redoStep = null;
	}

	/**
	 * Create holder containing the data from a {@link Supplier}.
	 * 
	 * @param supplier
	 *            supplier for the immutable data
	 */
	protected AbstractImmutableHolder(Supplier<I> supplier) {
		this.value = supplier.get();
		this.undoStep = null;
		this.redoStep = null;
	}

	/**
	 * Create new holder state by performing an operation on a previous holder
	 * state.
	 * 
	 * @param before
	 *            previous holder state
	 * @param operation
	 *            operation to perform
	 */
	protected AbstractImmutableHolder(H before, O operation) {
		// Unwrap chain and perform operation
		this.value = before.value.mutate(operation);
		this.redoStep = null;
		this.undoStep = before.unwrap(castSelf());
	}

	/**
	 * Create new holder functioning as undo step of a mutated holder state.
	 * When a new state is created, the previous step cannot be used as undo
	 * step, as its redo step would not be the new created state. So the chain
	 * of undo steps needs to be put in new holder objects, where each one has
	 * the same immutable data as it had before, but the undo and redo steps are
	 * linked to a holder of the new chain.
	 * 
	 * @param source
	 *            the original holder object for this state
	 * @param newRedoStep
	 *            the step that should be the new redo step of the created
	 *            object
	 */
	protected AbstractImmutableHolder(H source, H newRedoStep) {
		// Unwrap data and
		this.value = source.value;
		this.redoStep = newRedoStep;
		if (source.undoStep == null) {
			// Recursion end - no further undo step
			this.undoStep = null;
		} else {
			// Recursion step - unwrap and copy unext undo step
			this.undoStep = source.undoStep.unwrap(castSelf());
		}
	}

	/*
	 * --- Utility Methods ---
	 */

	/**
	 * Check if project has step to undo.
	 * 
	 * @return true if undo can be performed
	 */
	public boolean hasUndoStep() {
		return undoStep != null;
	}

	/**
	 * Check if project has step to redo.
	 * 
	 * @return true if redo can be performed
	 */
	public boolean hasRedoStep() {
		return redoStep != null;
	}

	/**
	 * Return undo step for this.
	 * 
	 * @return step before
	 */
	public H undo() {
		if (!hasUndoStep()) {
			// No undo available - return current state
			return castSelf();
		}
		return undoStep;
	}

	/**
	 * Return redo step for this.
	 * 
	 * @return step afterwards
	 */
	public H redo() {
		if (!hasRedoStep()) {
			// No redo available - return current state
			return castSelf();
		}
		return redoStep;
	}

	/**
	 * Supplier for an empty value
	 * 
	 * @return empty value
	 */
	protected abstract I createEmpty();

	/**
	 * Cast self (to implementor class &lt;H&gt;).
	 * 
	 * @return this as &lt;H&gt;
	 */
	protected abstract H castSelf();

	/**
	 * Call the constructor
	 * {@link AbstractImmutableHolder#AbstractImmutableHolder(AbstractImmutableHolder, AbstractImmutableHolder)}
	 * implementation (of the implementor class &lt;H&gt;).
	 * 
	 * @param newRedoStep
	 *            holder state which should be the updated redo step of the
	 *            unwrapped holder state
	 * @return new holder state
	 */
	protected abstract H unwrap(H newRedoStep);

	/**
	 * Perform operation (cast to implementor class).
	 * 
	 * @param operation
	 *            operation to perform
	 * @return changed state
	 */
	public abstract H operate(O operation);

}
