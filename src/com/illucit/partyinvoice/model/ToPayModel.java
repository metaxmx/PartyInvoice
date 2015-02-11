package com.illucit.partyinvoice.model;

import com.illucit.partyinvoice.Localization;
import com.illucit.partyinvoice.immutabledata.ImmutableGroup;
import com.illucit.partyinvoice.immutabledata.ImmutablePerson;

/**
 * Model for "To Pay" dropdown list.
 * 
 * @author Christian Simon
 *
 */
public class ToPayModel {

	/**
	 * "To pay" category type.
	 * 
	 * @author Christian Simon
	 *
	 */
	public enum ToPayType {
		/** All persons have to pay an equal amount. */
		All,
		/** ALl persons in a group have to pay an equal amount. */
		Group,
		/** One person alone has to pay the full amount. */
		Person
	}

	private ImmutableGroup group;

	private ImmutablePerson person;

	/**
	 * Create model for {@link ToPayType#All}.
	 */
	public ToPayModel() {
		this.person = null;
		this.group = null;
	}

	/**
	 * Create model for {@link ToPayType#Person}.
	 * 
	 * @param person
	 *            person who has to pay
	 */
	public ToPayModel(ImmutablePerson person) {
		update(person);
	}

	/**
	 * Create model for {@link ToPayType#Group}.
	 * 
	 * @param group
	 *            group who has to pay
	 */
	public ToPayModel(ImmutableGroup group) {
		update(group);
	}

	/**
	 * Update data for a model with {@link ToPayType#Person}.
	 * 
	 * @param person
	 *            updated person data
	 */
	public void update(ImmutablePerson person) {
		this.person = person;
		this.group = null;
	}

	/**
	 * Update data for a model with {@link ToPayType#Group}.
	 * 
	 * @param group
	 *            updated group data
	 */
	public void update(ImmutableGroup group) {
		this.person = null;
		this.group = group;
	}

	/**
	 * Get type.
	 * 
	 * @return to pay type
	 */
	public ToPayType getType() {
		if (person != null) {
			return ToPayType.Person;
		} else if (group != null) {
			return ToPayType.Group;
		}
		return ToPayType.All;
	}

	/**
	 * Get group for a model with {@link ToPayType#Group}.
	 * 
	 * @return group
	 */
	public ImmutableGroup getGroup() {
		return group;
	}

	/**
	 * Get person for a model with {@link ToPayType#Person}.
	 * 
	 * @return person
	 */
	public ImmutablePerson getPerson() {
		return person;
	}

	/**
	 * Get title of "to pay" model.
	 * 
	 * @return title in list
	 */
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
