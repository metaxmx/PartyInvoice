package com.illucit.partyinvoice.data;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

public class Group implements Serializable {

	private static final long serialVersionUID = 709485074492889160L;

	/*
	 * Attributes
	 */

	private String name;

	private List<String> personNames;

	/*
	 * Transient Attributes
	 */

	@TransientValue
	private Project project;

	@CalculatedValue
	@TransientValue
	private List<Person> persons;

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

	/*
	 * Utility Methods and Transient Getters / Setters
	 */

	@XmlTransient
	public Project getProject() {
		return project;
	}

	public void assign(Project project) {
		this.project = project;
		persons = personNames.stream().map(pn -> project.getPerson(pn)).filter(p -> p != null).collect(toList());
	}
}
