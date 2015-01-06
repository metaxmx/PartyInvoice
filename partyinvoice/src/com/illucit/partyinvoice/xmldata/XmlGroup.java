package com.illucit.partyinvoice.xmldata;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.illucit.partyinvoice.data.Group;

public class XmlGroup implements Group, Serializable {

	private static final long serialVersionUID = 709485074492889160L;

	/*
	 * Attributes
	 */

	private String name;

	private List<String> personNames;

	/*
	 * Attribute Getters
	 */

	@XmlElement(name = "Name")
	public String getName() {
		return name;
	}

	@XmlElementWrapper(name = "Persons")
	@XmlElement(name = "Person")
	public List<String> getPersonNames() {
		return personNames;
	}

	/*
	 * Attribute Setters
	 */

	public void setName(String name) {
		this.name = name;
	}

	public void setPersonNames(List<String> personNames) {
		this.personNames = personNames;
	}
}
