package com.illucit.partyinvoice.immutabledata;

import com.illucit.partyinvoice.xmldata.Project;

public class ImmutableProject implements Mutation<Operation, ImmutableProject> {

	private static final long serialVersionUID = -734021803698594857L;

	/*
	 * --- Members ---
	 */

	private final String title;

	/*
	 * --- Getters ---
	 */

	public String getTitle() {
		return title;
	}

	/*
	 * --- Constructors ---
	 */

	/**
	 * Create new, empty ImmutableProject.
	 */
	public ImmutableProject() {
		this.title = "untitled";
	}

	/**
	 * Copy constructor (Loading from XML).
	 * 
	 * @param project
	 */
	public ImmutableProject(Project project) {
		this.title = project.getTitle();
	}

	public ImmutableProject(ImmutableProject currentState, Operation operation) {
		String title = this.title;

		// TODO: Operation

		this.title = title;
	}

	/*
	 * --- Utility Methods ---
	 */

	public ImmutableProject mutate(Operation operation) {
		return new ImmutableProject(this, operation);
	};

	/**
	 * Copy to XML Structure (Save to XML).
	 * 
	 * @return mutable prject with JAXB annotations
	 */
	public Project copyToProject() {
		Project project = new Project();
		return project;
	}

}
