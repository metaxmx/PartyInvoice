package com.illucit.partyinvoice.immutabledata;

import com.illucit.partyinvoice.data.Operation;
import com.illucit.partyinvoice.xmldata.XmlProject;

/**
 * {@link AbstractImmutableHolder} implementation for {@link ImmutableProject}
 * base data class.
 * 
 * @author Christian Simon
 *
 */
public final class ImmutableProjectHolder extends
		AbstractImmutableHolder<Operation, ImmutableProject, ImmutableProjectHolder> {

	private static final long serialVersionUID = -7478773282181850364L;

	/**
	 * Create emtpy project holder.
	 */
	public ImmutableProjectHolder() {
		super();
	}

	/**
	 * Create project holder from loaded XML project data.
	 * 
	 * @param project
	 *            XML project data
	 */
	public ImmutableProjectHolder(XmlProject project) {
		super(() -> new ImmutableProject(project));
	}

	/**
	 * Create project holder state from unwrap operation.
	 * 
	 * @param source
	 *            holder data source
	 * @param newRedoStep
	 *            next redo step
	 */
	private ImmutableProjectHolder(ImmutableProjectHolder source, ImmutableProjectHolder newRedoStep) {
		super(source, newRedoStep);
	}

	/**
	 * Create holder state from performed operation.
	 * 
	 * @param before
	 *            state before
	 * @param operation
	 *            operation that is performed
	 */
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

	/**
	 * Get the {@link ImmutableProject} of the current state.
	 * 
	 * @return project data
	 */
	public ImmutableProject getProject() {
		return getValue();
	}

}
