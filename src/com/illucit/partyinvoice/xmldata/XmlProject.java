package com.illucit.partyinvoice.xmldata;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Project data for XML serialization.
 * 
 * @author Christian Simon
 *
 */
@SuppressWarnings("javadoc")
@XmlRootElement(name = "PartyInvoiceProject")
public class XmlProject implements Serializable {

	private static final long serialVersionUID = -1878999984953234565L;

	/*
	 * Attributes
	 */

	private String version = "1.0";

	private String title;

	private List<XmlPerson> persons = new LinkedList<>();

	private List<XmlGroup> groups = new LinkedList<>();

	private List<XmlInvoice> invoices = new LinkedList<>();

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
	public List<XmlPerson> getPersons() {
		return persons;
	}

	@XmlElementWrapper(name = "Groups")
	@XmlElement(name = "Group")
	public List<XmlGroup> getGroups() {
		return groups;
	}

	@XmlElementWrapper(name = "Invoices")
	@XmlElement(name = "Invoice")
	public List<XmlInvoice> getInvoices() {
		return invoices;
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

	public void setPersons(List<XmlPerson> persons) {
		this.persons = persons;
	}

	public void setGroups(List<XmlGroup> groups) {
		this.groups = groups;
	}

	public void setInvoices(List<XmlInvoice> invoices) {
		this.invoices = invoices;
	}

}
