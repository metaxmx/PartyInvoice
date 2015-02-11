package com.illucit.partyinvoice.immutabledata;

import com.illucit.partyinvoice.data.Mutation;

/**
 * Test data for {@link AbstractImmutableHolderTest}.
 * 
 * @author Christian Simon
 *
 */
@SuppressWarnings("javadoc")
public class ImmutablePoint implements Mutation<TranslationOperation, ImmutablePoint> {

	private static final long serialVersionUID = 8248503763887144467L;

	private final int x;

	private final int y;

	public ImmutablePoint() {
		this.x = 0;
		this.y = 0;
	}

	public ImmutablePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public ImmutablePoint(ImmutablePoint oldState, TranslationOperation op) {
		this.x = oldState.x + op.getX();
		this.y = oldState.y + op.getY();
	}

	@Override
	public ImmutablePoint mutate(TranslationOperation operation) {
		return new ImmutablePoint(this, operation);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
