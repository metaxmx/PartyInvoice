package com.illucit.partyinvoice.immutabledata;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.illucit.partyinvoice.data.Group;
import com.illucit.partyinvoice.data.Invoice;
import com.illucit.partyinvoice.data.Item;
import com.illucit.partyinvoice.data.Mutation;
import com.illucit.partyinvoice.data.Operation;
import com.illucit.partyinvoice.immutabledata.operation.AddPersonOp;
import com.illucit.partyinvoice.immutabledata.operation.DelPersonOp;
import com.illucit.partyinvoice.immutabledata.operation.RenamePersonOp;
import com.illucit.partyinvoice.xmldata.XmlGroup;
import com.illucit.partyinvoice.xmldata.XmlInvoice;
import com.illucit.partyinvoice.xmldata.XmlItem;
import com.illucit.partyinvoice.xmldata.XmlPerson;
import com.illucit.partyinvoice.xmldata.XmlProject;

public class ImmutableProject implements Mutation<Operation, ImmutableProject> {

	private static final long serialVersionUID = -734021803698594857L;

	/*
	 * --- Members ---
	 */

	private final String title;

	private final List<ImmutablePerson> persons;

	private final List<ImmutableGroup> groups;

	private final List<ImmutableInvoice> invoices;

	/*
	 * --- Getters ---
	 */

	public String getTitle() {
		return title;
	}

	public List<ImmutablePerson> getPersons() {
		return persons;
	}

	public List<ImmutableGroup> getGroups() {
		return groups;
	}

	public List<ImmutableInvoice> getInvoices() {
		return invoices;
	}

	/*
	 * --- Constructors ---
	 */

	/**
	 * Create new, empty ImmutableProject.
	 */
	public ImmutableProject() {
		this.title = "untitled";
		this.persons = ImmutableList.of();
		this.groups = ImmutableList.of();
		this.invoices = ImmutableList.of();
	}

	/**
	 * Copy constructor (Loading from XML).
	 * 
	 * @param project
	 */
	public ImmutableProject(XmlProject project) {
		this(project.getTitle(), getPersonList(project), getGroupList(project), getInvoiceList(project));
	}

	public ImmutableProject(ImmutableProject currentState, Operation operation) {
		this(currentState.getTitle(), getPersonList(currentState, operation), getGroupList(currentState, operation),
				getInvoiceList(currentState, operation));
	}

	private ImmutableProject(String title, List<String> personNames, List<? extends Group> groups,
			List<? extends Invoice> invoices) {

		Map<String, ImmutablePerson> personsByName = new HashMap<>();
		Map<String, Long> shareByPerson = new HashMap<>();
		Map<String, Long> paidByPerson = new HashMap<>();

		Map<String, Long> shareByGroup = new HashMap<>();

		long shareForAll = 0l;

		// Calculate share for each item
		for (Invoice invoice : invoices) {
			String paidBy = invoice.getPaidBy();
			for (Item item : invoice.getItems()) {
				String itemPaidBy = paidBy;
				if (item.getPaidBy() != null) {
					// Override per item
					itemPaidBy = item.getPaidBy();
				}
				long total = item.getTotal();
				mapInc(paidByPerson, itemPaidBy, total);
				paidByPerson.merge(itemPaidBy, total, (oldValue, inc) -> oldValue + inc);

				if (item.getPersonToPay() != null) {
					mapInc(shareByPerson, item.getPersonToPay(), total);
				} else if (item.getGroupToPay() != null) {
					mapInc(shareByGroup, item.getGroupToPay(), total);
				} else {
					shareForAll += total;
				}
			}
		}
		// Divide group shares
		if (!shareByGroup.isEmpty()) {
			for (Group group : groups) {
				long share = shareByGroup.getOrDefault(group.getName(), 0l);
				if (share > 0) {
					for (String personName : group.getPersonNames()) {
						mapInc(shareByPerson, personName, share);
					}
				}
			}
		}
		// Divide all shares
		if (!personNames.isEmpty()) {
			long share = shareForAll / personNames.size();
			for (String personName : personNames) {
				mapInc(shareByPerson, personName, share);
			}
		}

		this.title = title;

		List<ImmutablePerson> immutablePersons = new LinkedList<>();
		for (String personName : personNames) {
			long share = shareByPerson.getOrDefault(personName, 0l);
			long paid = paidByPerson.getOrDefault(personName, 0l);
			ImmutablePerson immutablePerson = new ImmutablePerson(personName, paid, share);
			immutablePersons.add(immutablePerson);
			personsByName.put(personName, immutablePerson);
		}
		this.persons = ImmutableList.copyOf(immutablePersons);

		List<ImmutableGroup> immutableGroups = new LinkedList<>();
		for (Group group : groups) {
			List<ImmutablePerson> groupPersons = group.getPersonNames().stream()
					.filter(name -> personsByName.containsKey(name)).map(name -> personsByName.get(name))
					.collect(toList());
			immutableGroups.add(new ImmutableGroup(group.getName(), ImmutableList.copyOf(groupPersons)));
		}
		this.groups = ImmutableList.copyOf(immutableGroups);

		List<ImmutableInvoice> immutableInvoices = new LinkedList<>();
		for (Invoice invoice : invoices) {
			List<ImmutableItem> immutableItems = new LinkedList<>();
			for (Item item : invoice.getItems()) {
				ImmutableItem immutableItem = new ImmutableItem(item.getName(), item.getPrice(), item.getQuantity(),
						item.getPaidBy(), item.getGroupToPay(), item.getPersonToPay());
				immutableItems.add(immutableItem);
			}
			ImmutableInvoice immutableInvoice = new ImmutableInvoice(invoice.getTitle(), invoice.getPaidBy(),
					immutableItems);
			immutableInvoices.add(immutableInvoice);
		}
		this.invoices = ImmutableList.copyOf(immutableInvoices);
	}

	/*
	 * --- Utility Methods ---
	 */

	private static <T> void mapInc(Map<T, Long> map, T key, long value) {
		map.merge(key, value, (oldValue, inc) -> oldValue + inc);
	}

	private static List<String> getPersonList(XmlProject project) {
		return project.getPersons().stream().map(XmlPerson::getName).collect(toList());
	}

	private static List<String> getPersonList(ImmutableProject project, Operation operation) {
		List<String> persons = new LinkedList<>();
		for (ImmutablePerson person : project.getPersons()) {
			String personName = person.getName();
			if (operation instanceof DelPersonOp) {
				DelPersonOp op = (DelPersonOp) operation;
				if (op.getName().equals(personName)) {
					continue;
				}
			}
			if (operation instanceof RenamePersonOp) {
				RenamePersonOp op = (RenamePersonOp) operation;
				if (op.getOldName().equals(personName)) {
					persons.add(op.getNewName());
					continue;
				}
			}
			persons.add(personName);
		}
		if (operation instanceof AddPersonOp) {
			AddPersonOp op = (AddPersonOp) operation;
			persons.add(op.getName());
		}
		return persons;
	}

	private static List<? extends Group> getGroupList(XmlProject project) {
		return project.getGroups();
	}

	private static List<? extends Group> getGroupList(ImmutableProject project, Operation operation) {
		// TODO: Handle operations
		return project.getGroups();
	}

	private static List<? extends Invoice> getInvoiceList(XmlProject project) {
		return project.getInvoices();
	}

	private static List<? extends Invoice> getInvoiceList(ImmutableProject project, Operation operation) {
		// TODO: Handle operations
		return project.getInvoices();
	}

	public ImmutableProject mutate(Operation operation) {
		return new ImmutableProject(this, operation);
	};

	/**
	 * Copy to XML Structure (Save to XML).
	 * 
	 * @return mutable prject with JAXB annotations
	 */
	public XmlProject getXmlProject() {
		XmlProject project = new XmlProject();
		project.setTitle(getTitle());
		for (ImmutablePerson person : getPersons()) {
			XmlPerson p = new XmlPerson();
			p.setName(person.getName());
			p.setShare(person.getShare());
			p.setPaid(person.getPaid());
			p.setDifference(person.getDifference());
			project.getPersons().add(p);
		}
		for (ImmutableGroup group : getGroups()) {
			XmlGroup g = new XmlGroup();
			g.setName(group.getName());
			g.setPersonNames(new LinkedList<>(group.getPersonNames()));
			project.getGroups().add(g);
		}
		for (ImmutableInvoice invoice : getInvoices()) {
			XmlInvoice iv = new XmlInvoice();
			iv.setTitle(invoice.getTitle());
			iv.setPaidBy(invoice.getPaidBy());
			List<XmlItem> items = new LinkedList<>();
			for (ImmutableItem item : invoice.getItems()) {
				XmlItem i = new XmlItem();
				i.setName(item.getName());
				i.setPaidBy(item.getPaidBy());
				i.setPrice(item.getPrice());
				i.setQuantity(item.getQuantity());
				i.setTotal(item.getTotal());
				i.setGroupToPay(item.getGroupToPay());
				i.setPersonToPay(item.getPersonToPay());
				items.add(i);
			}
			iv.setItems(items);
			project.getInvoices().add(iv);
		}
		return project;
	}

}
