package com.illucit.partyinvoice.immutabledata;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.List;

import com.illucit.partyinvoice.data.Group;

public class ImmutableGroup implements Group, Serializable {

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

	public List<ImmutablePerson> getPersons() {
		return persons;
	}

	@Override
	public List<String> getPersonNames() {
		return persons.stream().map(ImmutablePerson::getName).collect(toList());
	}

	/*
	 * --- Constructors ---
	 */

	public ImmutableGroup(String name, List<ImmutablePerson> persons) {
		this.name = name;
		this.persons = persons;
	}

}
