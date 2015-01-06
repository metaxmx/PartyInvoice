package com.illucit.partyinvoice.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

import com.illucit.partyinvoice.xmldata.XmlPerson;

/**
 * Model for a person
 * 
 * @author Christian Simon
 *
 */
public class PersonModel {

	private final XmlPerson person;

	private final StringProperty name;

	/**
	 * Weight for cost distribution. Costs are split by sum of weights, where
	 * every person gets a share in the amout of his/her weight.
	 */
	// private final FloatProperty calcWeight;

	public PersonModel(XmlPerson person) {
		this.person = person;

		try {

			this.name = new JavaBeanStringPropertyBuilder().bean(person).name("name").build();

		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public StringProperty getName() {
		return name;
	}

	public XmlPerson getPerson() {
		return person;
	}

}
