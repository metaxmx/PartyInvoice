package com.illucit.partyinvoice.immutabledata;

import com.illucit.partyinvoice.data.Operation;
import com.illucit.partyinvoice.xmldata.XmlProject;

public final class ImmutableProjectHolder extends
		AbstractImmutableHolder<Operation, ImmutableProject, ImmutableProjectHolder> {

	private static final long serialVersionUID = -7478773282181850364L;

	public ImmutableProjectHolder() {
		super();
	}

	public ImmutableProjectHolder(XmlProject project) {
		super(() -> new ImmutableProject(project));
	}

	private ImmutableProjectHolder(ImmutableProjectHolder source, ImmutableProjectHolder newRedoStep) {
		super(source, newRedoStep);
	}

	public ImmutableProjectHolder(ImmutableProjectHolder before, Operation operation) {
		super(before, operation);
	}

	@Override
	protected ImmutableProject createEmpty() {
		return new ImmutableProject();
	}

	@Override
	protected ImmutableProjectHolder castSelf() {
		return this;
	}

	@Override
	protected ImmutableProjectHolder unwrap(ImmutableProjectHolder newRedoStep) {
		return new ImmutableProjectHolder(this, newRedoStep);
	}

	@Override
	public ImmutableProjectHolder operate(Operation operation) {
		return new ImmutableProjectHolder(this, operation);
	}

	public ImmutableProject getProject() {
		return getValue();
	}

}
