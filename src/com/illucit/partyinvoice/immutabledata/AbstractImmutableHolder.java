package com.illucit.partyinvoice.immutabledata;

import java.io.Serializable;
import java.util.function.Supplier;

import com.illucit.partyinvoice.data.Mutation;

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

	public AbstractImmutableHolder() {
		this.value = createEmpty();
		this.undoStep = null;
		this.redoStep = null;
	}

	protected AbstractImmutableHolder(Supplier<I> supplier) {
		this.value = supplier.get();
		this.undoStep = null;
		this.redoStep = null;
	}

	protected AbstractImmutableHolder(H before, O operation) {
		// Unwrap chain and perform operation
		this.value = before.value.mutate(operation);
		this.redoStep = null;
		this.undoStep = before.unwrap(castSelf());
	}

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
	 * Cast self (to parameter class H).
	 * 
	 * @return this as H
	 */
	protected abstract H castSelf();

	protected abstract H unwrap(H newRedoStep);

	public abstract H operate(O operation);

}
