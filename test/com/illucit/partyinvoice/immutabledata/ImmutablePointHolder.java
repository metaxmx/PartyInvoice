package com.illucit.partyinvoice.immutabledata;

/**
 * Test data for {@link AbstractImmutableHolderTest}.
 * 
 * @author Christian Simon
 *
 */
@SuppressWarnings("javadoc")
public final class ImmutablePointHolder extends
		AbstractImmutableHolder<TranslationOperation, ImmutablePoint, ImmutablePointHolder> {

	private static final long serialVersionUID = 1838871660633535545L;

	public ImmutablePointHolder() {
		super();
	}

	private ImmutablePointHolder(ImmutablePointHolder source, ImmutablePointHolder newRedoStep) {
		super(source, newRedoStep);
	}

	public ImmutablePointHolder(int x, int y) {
		super(() -> new ImmutablePoint(x, y));
	}

	public ImmutablePointHolder(ImmutablePointHolder before, TranslationOperation operation) {
		super(before, operation);
	}

	@Override
	protected ImmutablePoint createEmpty() {
		return new ImmutablePoint();
	}

	@Override
	protected ImmutablePointHolder castSelf() {
		return this;
	}

	@Override
	protected ImmutablePointHolder unwrap(ImmutablePointHolder newRedoStep) {
		return new ImmutablePointHolder(this, newRedoStep);
	}

	@Override
	public ImmutablePointHolder operate(TranslationOperation operation) {
		return new ImmutablePointHolder(this, operation);
	}

}
