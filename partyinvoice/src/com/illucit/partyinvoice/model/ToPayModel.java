package com.illucit.partyinvoice.model;

import com.illucit.partyinvoice.Localization;
import com.illucit.partyinvoice.immutabledata.ImmutableGroup;
import com.illucit.partyinvoice.immutabledata.ImmutablePerson;

public class ToPayModel {

	public enum ToPayType {
		All, Group, Person
	}

	private ImmutableGroup group;

	private ImmutablePerson person;

	public ToPayModel() {
		this.person = null;
		this.group = null;
	}

	public ToPayModel(ImmutablePerson person) {
		update(person);
	}

	public ToPayModel(ImmutableGroup group) {
		update(group);
	}

	public void update(ImmutablePerson person) {
		this.person = person;
		this.group = null;
	}

	public void update(ImmutableGroup group) {
		this.person = null;
		this.group = group;
	}

	public ToPayType getType() {
		if (person != null) {
			return ToPayType.Person;
		} else if (group != null) {
			return ToPayType.Group;
		}
		return ToPayType.All;
	}

	public ImmutableGroup getGroup() {
		return group;
	}

	public ImmutablePerson getPerson() {
		return person;
	}

	public String getTitle() {
		switch (getType()) {
		case All:
			return Localization.getInstance().getString("ui.topay.all");

		case Person:
			return person.getName();

		case Group:
			return group.getName();
		}
		return "";
	}

	@Override
	public String toString() {
		return getTitle();
	}

}
