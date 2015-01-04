package com.illucit.partyinvoice.xmldata;

import static java.util.stream.Collectors.toMap;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PartyInvoiceProject")
public class Project implements Serializable {

	private static final long serialVersionUID = -1878999984953234565L;

	/*
	 * Attributes
	 */

	private String version = "1.0";

	private String title;

	private List<Person> persons = new LinkedList<>();

	private List<Group> groups = new LinkedList<>();

	/*
	 * Transient Attributes
	 */

	private Map<String, Person> personsByName = new Hashtable<>();

	private Map<String, Group> groupsByName = new Hashtable<>();

	/*
	 * Attribute Getters
	 */

	@XmlAttribute
	public String getVersion() {
		return version;
	}

	@XmlElement(name = "Title")
	public String getTitle() {
		return title;
	}

	@XmlElementWrapper(name = "Persons")
	@XmlElement(name = "Person")
	public List<Person> getPersons() {
		return persons;
	}

	@XmlElementWrapper(name = "Groups")
	@XmlElement(name = "Group")
	public List<Group> getGroups() {
		return groups;
	}

	/*
	 * Attribute Setters
	 */

	public void setVersion(String version) {
		this.version = version;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	/*
	 * Utility Methods and Transient Getters / Setters
	 */

	public Person getPerson(String name) {
		return personsByName.get(name);
	}

	public Group getGroup(String name) {
		return groupsByName.get(name);
	}

	public void calculate() {
		// TODO:
	}

	public void assign() {
		personsByName = persons.stream().filter(p -> p.getName() != null).collect(toMap(p -> p.getName(), p -> p));
		groupsByName = groups.stream().filter(g -> g.getName() != null).collect(toMap(g -> g.getName(), g -> g));
		persons.forEach(p -> p.assign(this));
		groups.forEach(g -> g.assign(this));
	}

}
