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
import com.illucit.partyinvoice.data.Person;
import com.illucit.partyinvoice.immutabledata.operation.AddInvoiceOp;
import com.illucit.partyinvoice.immutabledata.operation.AddItemOp;
import com.illucit.partyinvoice.immutabledata.operation.AddPersonOp;
import com.illucit.partyinvoice.immutabledata.operation.ChangeInvoiceOp;
import com.illucit.partyinvoice.immutabledata.operation.ChangeItemOp;
import com.illucit.partyinvoice.immutabledata.operation.ChangePersonOp;
import com.illucit.partyinvoice.immutabledata.operation.DelInvoiceOp;
import com.illucit.partyinvoice.immutabledata.operation.DelItemOp;
import com.illucit.partyinvoice.immutabledata.operation.DelPersonOp;
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

	private ImmutableProject(String title, List<? extends Person> persons, List<? extends Group> groups,
			List<? extends Invoice> invoices) {

		Map<Integer, ImmutablePerson> personsById = new HashMap<>();
		Map<Integer, ImmutableGroup> groupsById = new HashMap<>();

		Map<Integer, Long> shareByPerson = new HashMap<>();
		Map<Integer, Long> paidByPerson = new HashMap<>();

		Map<Integer, Long> shareByGroup = new HashMap<>();

		long shareForAll = 0l;

		// Calculate share for each item
		for (Invoice invoice : invoices) {
			int paidBy = invoice.getPaidBy();
			for (Item item : invoice.getItems()) {
				int itemPaidBy = paidBy;
				if (item.getPaidBy() != null) {
					// Override per item
					itemPaidBy = item.getPaidBy();
				}
				long total = item.getTotal();
				mapInc(paidByPerson, itemPaidBy, total);

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
				long shareForGroup = shareByGroup.getOrDefault(group.getId(), 0l);
				if (shareForGroup > 0) {
					long share = shareForGroup / group.getPersons().size();
					for (Integer personId : group.getPersons()) {
						mapInc(shareByPerson, personId, share);
					}
				}
			}
		}
		// Divide all shares
		if (!persons.isEmpty()) {
			long share = shareForAll / persons.size();
			for (Person person : persons) {
				mapInc(shareByPerson, person.getId(), share);
			}
		}

		this.title = title;

		List<ImmutablePerson> immutablePersons = new LinkedList<>();
		for (Person person : persons) {
			long share = shareByPerson.getOrDefault(person.getId(), 0l);
			long paid = paidByPerson.getOrDefault(person.getId(), 0l);
			ImmutablePerson immutablePerson = new ImmutablePerson(person.getId(), person.getName(), paid, share);
			immutablePersons.add(immutablePerson);
			personsById.put(person.getId(), immutablePerson);
		}
		this.persons = ImmutableList.copyOf(immutablePersons);

		List<ImmutableGroup> immutableGroups = new LinkedList<>();
		for (Group group : groups) {
			List<ImmutablePerson> groupPersons = group.getPersons().stream().filter(id -> personsById.containsKey(id))
					.map(id -> personsById.get(id)).collect(toList());
			ImmutableGroup immutableGroup = new ImmutableGroup(group.getId(), group.getName(),
					ImmutableList.copyOf(groupPersons));
			immutableGroups.add(immutableGroup);
			groupsById.put(group.getId(), immutableGroup);
		}
		this.groups = ImmutableList.copyOf(immutableGroups);

		List<ImmutableInvoice> immutableInvoices = new LinkedList<>();
		for (Invoice invoice : invoices) {
			List<ImmutableItem> immutableItems = new LinkedList<>();
			for (Item item : invoice.getItems()) {
				ImmutableItem immutableItem = new ImmutableItem(item.getId(), item.getTitle(), item.getPrice(),
						item.getQuantity(), item.getPaidBy() == null ? null : personsById.get(item.getPaidBy()),
						item.getGroupToPay() == null ? null : groupsById.get(item.getGroupToPay()),
						item.getPersonToPay() == null ? null : personsById.get(item.getPersonToPay()));
				immutableItems.add(immutableItem);
			}
			ImmutableInvoice immutableInvoice = new ImmutableInvoice(invoice.getId(), invoice.getTitle(),
					personsById.get(invoice.getPaidBy()), immutableItems);
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

	private static List<? extends Person> getPersonList(XmlProject project) {
		return project.getPersons();
	}

	private static List<? extends Person> getPersonList(ImmutableProject project, Operation operation) {
		List<PersonDTO> persons = new LinkedList<>();
		for (ImmutablePerson person : project.getPersons()) {
			if (operation instanceof DelPersonOp) {
				DelPersonOp op = (DelPersonOp) operation;
				if (op.getId() == person.getId()) {
					continue;
				}
			}
			if (operation instanceof ChangePersonOp) {
				ChangePersonOp op = (ChangePersonOp) operation;
				if (op.getId() == person.getId()) {
					persons.add(new PersonDTO(op.getId(), op.getNewName()));
					continue;
				}
			}
			persons.add(new PersonDTO(person));
		}
		if (operation instanceof AddPersonOp) {
			int nextAddPersonId = 1 + project.getPersons().stream().mapToInt(Person::getId).max().orElse(0);
			AddPersonOp op = (AddPersonOp) operation;
			persons.add(new PersonDTO(nextAddPersonId, op.getName()));
		}
		return persons;
	}

	private static List<? extends Group> getGroupList(XmlProject project) {
		return project.getGroups();
	}

	private static List<ImmutableGroup> getGroupList(ImmutableProject project, Operation operation) {
		// TODO: Handle operations
		return project.getGroups();
	}

	private static List<? extends Invoice> getInvoiceList(XmlProject project) {
		return project.getInvoices();
	}

	private static List<InvoiceDTO> getInvoiceList(ImmutableProject project, Operation operation) {
		List<InvoiceDTO> invoices = new LinkedList<>();
		for (ImmutableInvoice invoice : project.getInvoices()) {
			if (operation instanceof DelInvoiceOp) {
				DelInvoiceOp op = (DelInvoiceOp) operation;
				if (op.getId() == invoice.getId()) {
					continue;
				}
			}
			if (operation instanceof ChangeInvoiceOp) {
				ChangeInvoiceOp op = (ChangeInvoiceOp) operation;
				if (op.getId() == invoice.getId()) {
					invoices.add(new InvoiceDTO(op.getId(), op.getNewTitle(), op.getPaidBy(), invoice.getItems()));
					continue;
				}
			}
			invoices.add(new InvoiceDTO(invoice.getId(), invoice.getTitle(), invoice.getPaidBy(), getItemList(
					project, invoice, operation)));
		}
		if (operation instanceof AddInvoiceOp) {
			int nextAddInvoiceId = 1 + project.getInvoices().stream().mapToInt(Invoice::getId).max().orElse(0);
			AddInvoiceOp op = (AddInvoiceOp) operation;
			invoices.add(new InvoiceDTO(nextAddInvoiceId, op.getTitle(), op.getPaidBy(), ImmutableList.of()));
		}
		return invoices;
	}

	private static List<ItemDTO> getItemList(ImmutableProject project, ImmutableInvoice invoice, Operation operation) {
		List<ItemDTO> items = new LinkedList<>();
		for (ImmutableItem item : invoice.getItems()) {
			if (operation instanceof DelItemOp) {
				DelItemOp op = (DelItemOp) operation;
				if (op.getId() == item.getId()) {
					continue;
				}
			}
			if (operation instanceof ChangeItemOp) {
				ChangeItemOp op = (ChangeItemOp) operation;
				if (op.getId() == item.getId()) {
					items.add(new ItemDTO(op.getId(), op.getTitle(), op.getPrice(), op.getQuantity(), op.getPaidBy(),
							op.getGroupToPay(), op.getPersonToPay()));
					continue;
				}
			}
			items.add(new ItemDTO(item));
		}
		if (operation instanceof AddItemOp) {
			AddItemOp op = (AddItemOp) operation;
			if (op.getInvoiceId() == invoice.getId()) {
				int nextAddItemId = 1 + project.getInvoices().stream().flatMap(iv -> iv.getItems().stream())
						.mapToInt(Item::getId).max().orElse(0);
				items.add(new ItemDTO(nextAddItemId, op.getTitle(), op.getPrice(), op.getQuantity(), op.getPaidBy(), op
						.getGroupToPay(), op.getPersonToPay()));

			}
		}
		return items;
	}

	@Override
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
			p.setId(person.getId());
			p.setName(person.getName());
			p.setShare(person.getShare());
			p.setPaid(person.getPaid());
			p.setDifference(person.getDifference());
			project.getPersons().add(p);
		}
		for (ImmutableGroup group : getGroups()) {
			XmlGroup g = new XmlGroup();
			g.setId(group.getId());
			g.setName(group.getName());
			g.setPersons(new LinkedList<>(group.getPersons()));
			project.getGroups().add(g);
		}
		for (ImmutableInvoice invoice : getInvoices()) {
			XmlInvoice iv = new XmlInvoice();
			iv.setId(invoice.getId());
			iv.setTitle(invoice.getTitle());
			iv.setPaidBy(invoice.getPaidBy());
			List<XmlItem> items = new LinkedList<>();
			for (ImmutableItem item : invoice.getItems()) {
				XmlItem i = new XmlItem();
				i.setId(item.getId());
				i.setTitle(item.getTitle());
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

	private static class PersonDTO implements Person {

		private final int id;

		private final String name;

		public PersonDTO(Person person) {
			this(person.getId(), person.getName());
		}

		public PersonDTO(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public int getId() {
			return id;
		}

		@Override
		public String getName() {
			return name;
		}

	}

	private static class InvoiceDTO implements Invoice {

		private final int id;

		private final String title;

		private final int paidBy;

		private final List<? extends Item> items;

		public InvoiceDTO(int id, String title, int paidBy, List<? extends Item> items) {
			this.id = id;
			this.title = title;
			this.paidBy = paidBy;
			this.items = items;
		}

		@Override
		public int getId() {
			return id;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public int getPaidBy() {
			return paidBy;
		}

		@Override
		public List<? extends Item> getItems() {
			return items;
		}

	}

	private static class ItemDTO implements Item {

		private final int id;

		private final String title;

		private final long price;

		private final int quantity;

		private final Integer paidBy;

		private final Integer groupToPay;

		private final Integer personToPay;

		public ItemDTO(int id, String title, long price, int quantity, Integer paidBy, Integer groupToPay,
				Integer personToPay) {
			this.id = id;
			this.title = title;
			this.price = price;
			this.quantity = quantity;
			this.paidBy = paidBy;
			this.groupToPay = groupToPay;
			this.personToPay = personToPay;
		}

		public ItemDTO(Item item) {
			this(item.getId(), item.getTitle(), item.getPrice(), item.getQuantity(), item.getPaidBy(), item
					.getGroupToPay(), item.getPersonToPay());
		}

		@Override
		public int getId() {
			return id;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public long getPrice() {
			return price;
		}

		@Override
		public int getQuantity() {
			return quantity;
		}

		@Override
		public Integer getPaidBy() {
			return paidBy;
		}

		@Override
		public Integer getGroupToPay() {
			return groupToPay;
		}

		@Override
		public Integer getPersonToPay() {
			return personToPay;
		}

	}

}
