package com.illucit.partyinvoice.immutabledata;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.illucit.partyinvoice.data.Group;

public class ImmutableGroup extends ImmutableBaseData implements Group {

	private static final long serialVersionUID = -8019203314162371256L;

	/*
	 * --- Members ---
	 */

	private final String name;

	private final List<ImmutablePerson> persons;

	/*
	 * --- Getters ---
	 */

	@Override
	public String getName() {
		return name;
	}

	public List<ImmutablePerson> getPersonsX() {
		return persons;
	}

	@Override
	public List<Integer> getPersons() {
		return persons.stream().map(ImmutablePerson::getId).collect(toList());
	}

	/*
	 * --- Constructors ---
	 */

	public ImmutableGroup(int id, String name, List<ImmutablePerson> persons) {
		super(id);
		this.name = name;
		this.persons = persons;
	}

}
