package com.illucit.partyinvoice.xmldata;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.illucit.partyinvoice.data.Group;

/**
 * Implementation of {@link Group} for XML serialization.
 * 
 * @author Christian Simon
 *
 */
@SuppressWarnings("javadoc")
public class XmlGroup implements Group, Serializable {

	private static final long serialVersionUID = 709485074492889160L;

	/*
	 * Attributes
	 */

	private int id;

	private String name;

	private List<Integer> persons;

	/*
	 * Attribute Getters
	 */

	@Override
	@XmlAttribute(name = "id", required = true)
	public int getId() {
		return id;
	}

	@Override
	@XmlElement(name = "Name")
	public String getName() {
		return name;
	}

	@Override
	@XmlElementWrapper(name = "Persons")
	@XmlElement(name = "Person")
	public List<Integer> getPersons() {
		return persons;
	}

	/*
	 * Attribute Setters
	 */

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPersons(List<Integer> persons) {
		this.persons = persons;
	}
}
