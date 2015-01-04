package com.illucit.partyinvoice.xmldata;

import static com.illucit.partyinvoice.CurrencyUtil.currencyToString;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Person implements Serializable {

	private static final long serialVersionUID = 7805314640133016613L;

	/*
	 * Attributes
	 */

	private String name;

	@CalculatedValue
	private Long paid;

	@CalculatedValue
	private Long share;

	@CalculatedValue
	private Long difference;

	/*
	 * Transient Attributes
	 */

	@TransientValue
	private Project project;

	/*
	 * Attribute Getters
	 */

	@XmlElement(name = "Name")
	public String getName() {
		return name;
	}

	@CalculatedValue
	@XmlElement(name = "AlreadyPaid")
	@XmlJavaTypeAdapter(value = CurrencyTypeAdapter.class)
	public Long getPaid() {
		return paid;
	}

	@CalculatedValue
	@XmlElement(name = "ShareToPay")
	@XmlJavaTypeAdapter(value = CurrencyTypeAdapter.class)
	public Long getShare() {
		return share;
	}

	@CalculatedValue
	@XmlElement(name = "StillToPay")
	@XmlJavaTypeAdapter(value = CurrencyTypeAdapter.class)
	public Long getDifference() {
		return difference;
	}

	/*
	 * Attribute Setters
	 */

	public void setName(String name) {
		this.name = name;
	}

	public void setPaid(Long paid) {
		this.paid = paid;
	}

	public void setShare(Long share) {
		this.share = share;
	}

	public void setDifference(Long difference) {
		this.difference = difference;
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
	}

	@XmlTransient
	public String getPaidStr() {
		return asCurrency(paid);
	}

	@XmlTransient
	public String getShareStr() {
		return asCurrency(share);
	}

	@XmlTransient
	public String getDifferenceStr() {
		return asCurrency(difference);
	}

	private static String asCurrency(Long value) {
		return value == null ? "" : currencyToString(value);
	}

	public void calculate() {
		if (paid == null || share == null) {
			difference = null;
		} else {
			difference = share - paid;
		}
	}

}
