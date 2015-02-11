package com.illucit.partyinvoice.view;

import static com.illucit.partyinvoice.util.CurrencyUtil.currencyToString;
import static com.illucit.partyinvoice.util.ExtendedBindings.conditionBinding;
import static com.illucit.partyinvoice.util.ExtendedBindings.resolvingBinding;
import static com.illucit.partyinvoice.util.Integers.zeroToNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import com.illucit.partyinvoice.AbstractController;
import com.illucit.partyinvoice.PartyInvoiceApp;
import com.illucit.partyinvoice.model.InvoiceModel;
import com.illucit.partyinvoice.model.ItemModel;
import com.illucit.partyinvoice.model.PersonListModel;
import com.illucit.partyinvoice.model.ToPayModel;
import com.illucit.partyinvoice.model.ToPayModel.ToPayType;

/**
 * Controller for "Manage invoice" view.
 * 
 * @author Christian Simon
 *
 */
public class InvoicesController extends AbstractController {

	/*
	 * Invoice
	 */

	@FXML
	private TableView<InvoiceModel> invoicesTable;

	@FXML
	private TableColumn<InvoiceModel, String> invoiceTitleCol;

	@FXML
	private TableColumn<InvoiceModel, PersonListModel> invoicePaidByCol;

	@FXML
	private TableColumn<InvoiceModel, String> invoiceItemsCol;

	@FXML
	private TableColumn<InvoiceModel, String> invoiceTotalCol;

	@FXML
	private TextField newInvoiceTitleField;

	@FXML
	private ChoiceBox<PersonListModel> newInvoicePaidByField;

	@FXML
	private Button addInvoiceButton;

	@FXML
	private Button deleteInvoiceButton;

	/*
	 * Item
	 */

	@FXML
	private AnchorPane itemAnchorPane;

	@FXML
	private TableView<ItemModel> itemTable;

	@FXML
	private TableColumn<ItemModel, String> itemTitleCol;

	@FXML
	private TableColumn<ItemModel, String> itemPriceCol;

	@FXML
	private TableColumn<ItemModel, String> itemQuantityCol;

	@FXML
	private TableColumn<ItemModel, String> itemTotalCol;

	@FXML
	private TableColumn<ItemModel, PersonListModel> itemPaidByCol;

	@FXML
	private TableColumn<ItemModel, ToPayModel> itemToPayCol;

	@FXML
	private TextField newItemTitleField;

	@FXML
	private TextField newItemPriceField;

	@FXML
	private TextField newItemQuantityField;

	@FXML
	private TextField newItemTotalField;

	@FXML
	private ChoiceBox<PersonListModel> newItemPaidByField;

	@FXML
	private ChoiceBox<ToPayModel> newItemToPayField;

	@FXML
	private Button addItemButton;

	@FXML
	private Button deleteItemButton;

	@Override
	public void setApp(PartyInvoiceApp app) {
		super.setApp(app);

		/*
		 * Invoices
		 */

		invoiceTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		invoicePaidByCol.setCellValueFactory(new PropertyValueFactory<>("paidByModel"));
		invoiceItemsCol.setCellValueFactory(new PropertyValueFactory<>("items"));
		invoiceTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

		invoiceTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
		invoiceTitleCol.setOnEditCommit(this::changeInvoiceTitle);

		invoicePaidByCol.setCellFactory(ChoiceBoxTableCell.forTableColumn(app.getPersonNameList()));
		invoicePaidByCol.setOnEditCommit(this::changeInvoicePaidBy);

		invoicesTable.setItems(app.getInvoiceList());

		newInvoicePaidByField.setItems(app.getPersonNameList());

		addInvoiceButton.disableProperty().bind(
				newInvoiceTitleField.textProperty().isEmpty()
						.or(newInvoicePaidByField.getSelectionModel().selectedItemProperty().isNull()));

		deleteInvoiceButton.disableProperty().bind(invoicesTable.getSelectionModel().selectedItemProperty().isNull());

		getApp().setSelectedInvoiceProperty(invoicesTable.getSelectionModel().selectedItemProperty());

		/*
		 * Items
		 */

		itemAnchorPane.visibleProperty().bind(invoicesTable.getSelectionModel().selectedItemProperty().isNotNull());

		itemTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("priceCurrency"));
		itemQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityString"));
		itemTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		itemPaidByCol.setCellValueFactory(new PropertyValueFactory<>("paidByModel"));
		itemToPayCol.setCellValueFactory(new PropertyValueFactory<>("toPayModel"));

		itemTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
		itemTitleCol.setOnEditCommit(this::changeItemTitle);

		itemPriceCol.setCellFactory(TextFieldTableCell.forTableColumn());
		itemPriceCol.setOnEditCommit(this::changeItemPrice);

		itemQuantityCol.setCellFactory(TextFieldTableCell.forTableColumn());
		itemQuantityCol.setOnEditCommit(this::changeItemQuantity);

		itemPaidByCol.setCellFactory(ChoiceBoxTableCell.forTableColumn(app.getPersonNameListNullable()));
		itemPaidByCol.setOnEditCommit(this::changeItemPaidBy);

		itemToPayCol.setCellFactory(ChoiceBoxTableCell.forTableColumn(app.getToPayList()));
		itemToPayCol.setOnEditCommit(this::changeItemToPay);

		itemTable.setItems(app.getItemList());

		newItemPaidByField.setItems(app.getPersonNameListNullable());
		newItemPaidByField.getSelectionModel().selectFirst();

		newItemToPayField.setItems(app.getToPayList());
		newItemToPayField.getSelectionModel().selectFirst();

		addItemButton
				.disableProperty()
				.bind(newItemTitleField
						.textProperty()
						.isEmpty()
						.or(conditionBinding(newItemPriceField.textProperty(), InvoicesController::isPriceInvalid))
						.or(conditionBinding(newItemQuantityField.textProperty(), InvoicesController::isQuantityInvalid)));

		newItemTotalField.textProperty().bind(
				resolvingBinding(newItemPriceField.textProperty(), newItemQuantityField.textProperty(),
						InvoicesController::resolveTotal));

		deleteItemButton.disableProperty().bind(itemTable.getSelectionModel().selectedItemProperty().isNull());

	}

	/**
	 * Check if a given input String is valid as price.
	 * 
	 * @param value
	 *            input String
	 * @return true if input was valid price
	 */
	private static boolean isPriceInvalid(String value) {
		return resolvePrice(value) == null;
	}

	/**
	 * Check if a given input String is valid as quantity.
	 * 
	 * @param value
	 *            input String
	 * @return true if input was valid quantity
	 */
	private static boolean isQuantityInvalid(String value) {
		return resolveQuantity(value) == null;
	}

	/**
	 * Resolving function to get the "total price" as (currency String) from a
	 * price and a quantity input String.
	 * 
	 * @param priceValue
	 *            price String to parse
	 * @param quantityValue
	 *            quantity String to parse
	 * @return total price as currency (with the currency representation of zero
	 *         if one of the input strings is invalid)
	 */
	private static String resolveTotal(String priceValue, String quantityValue) {
		long total = 0;
		Integer quantity = resolveQuantity(quantityValue);
		Long price = resolvePrice(priceValue);
		if (price != null && quantity != null) {
			total = price * quantity;
		}
		return currencyToString(total);
	}

	/**
	 * Parse quantity value from input String.
	 * 
	 * @param value
	 *            input String
	 * @return input parsed as quantity or null if parsing not possible
	 */
	private static Integer resolveQuantity(String value) {
		try {
			int quantity = Integer.parseInt(value);
			if (quantity < 1) {
				return null;
			}
			return quantity;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Pattern for price parsing.
	 */
	private static final Pattern pricePattern = Pattern.compile("^\\s*(-?)\\s*([0-9]*)([,.][0-9]{0,2})?(\\s*€?)?\\s*$");

	/**
	 * Parse price value from input String.
	 * 
	 * @param value
	 *            input String
	 * @return input parsed as currency or null if parsing not possible
	 */
	private static Long resolvePrice(String value) {
		Matcher priceMatcher = pricePattern.matcher(value);
		if (!priceMatcher.matches()) {
			return null;
		}
		long price = 0;
		String sectMinus = priceMatcher.group(1);
		String sect1 = priceMatcher.group(2);
		String sect2 = priceMatcher.group(3);
		if ((sect1 == null || sect1.isEmpty()) && sect2 == null) {
			return null;
		}
		if (sect1 != null && !sect1.isEmpty()) {
			price = Long.parseLong(sect1) * 100;
		}
		if (sect2 != null && !sect2.isEmpty()) {
			sect2 = sect2.substring(1); // remove leading ',' or '.'
			if (sect2.length() == 1) {
				price += Long.parseLong(sect2) * 10;
			} else if (sect2.length() == 2) {
				price += Long.parseLong(sect2);
			}
		}
		if (sectMinus.length() > 0) {
			price = -price;
		}
		return price;
	}

	/**
	 * Add new invoice.
	 */
	@FXML
	public void addNewInvoice() {
		String newTitle = newInvoiceTitleField.getText();
		int newPaidBy = newInvoicePaidByField.getSelectionModel().getSelectedItem().getId();
		getApp().addInvoice(newTitle, newPaidBy);
		newInvoiceTitleField.textProperty().set("");
		newInvoicePaidByField.getSelectionModel().clearSelection();
	}

	private void changeInvoiceTitle(CellEditEvent<InvoiceModel, String> event) {
		InvoiceModel model = event.getRowValue();
		String newTitle = event.getNewValue();
		if (newTitle.isEmpty()) {
			// Enforce Redraw
			event.getTableColumn().setVisible(false);
			event.getTableColumn().setVisible(true);
			return;
		} else {
			model.titleProperty().set(newTitle);
		}
		getApp().changeInvoice(model.getId(), model.titleProperty().get(), model.paidByProperty().get());
	}

	private void changeInvoicePaidBy(CellEditEvent<InvoiceModel, PersonListModel> event) {
		InvoiceModel model = event.getRowValue();
		PersonListModel newPerson = event.getNewValue();
		if (newPerson == null) {
			// Enforce Redraw
			event.getTableColumn().setVisible(false);
			event.getTableColumn().setVisible(true);
			return;
		} else {
			model.paidByProperty().set(newPerson.getId());
		}
		getApp().changeInvoice(model.getId(), model.titleProperty().get(), model.paidByProperty().get());
	}

	/**
	 * Delete selected invoice.
	 */
	@FXML
	public void deleteInvoice() {
		InvoiceModel selectedModel = invoicesTable.selectionModelProperty().get().selectedItemProperty().get();
		if (selectedModel != null) {
			getApp().deleteInvoice(selectedModel.getId());
		}
	}

	/**
	 * Add new item to selected invoice.
	 */
	@FXML
	public void addNewItem() {
		String newTitle = newItemTitleField.getText();
		Integer quantity = resolveQuantity(newItemQuantityField.getText());
		Long price = resolvePrice(newItemPriceField.getText());
		Integer paidBy = null;
		if (!newItemPaidByField.getSelectionModel().isEmpty() && newItemPaidByField.getValue().getId() > 0) {
			paidBy = newItemPaidByField.getValue().getId();
		}
		Integer personToPay = null;
		Integer groupToPay = null;
		if (!newItemToPayField.getSelectionModel().isEmpty()) {
			ToPayModel toPay = newItemToPayField.getValue();
			personToPay = toPay.getType() == ToPayType.Person ? toPay.getPerson().getId() : null;
			groupToPay = toPay.getType() == ToPayType.Group ? toPay.getGroup().getId() : null;
		}

		if (quantity == null || price == null) {
			return;
		}
		getApp().addItem(newTitle, price, quantity, paidBy, personToPay, groupToPay);
		newItemTitleField.textProperty().set("");
		newItemQuantityField.textProperty().set("");
		newItemPriceField.textProperty().set("");
		newItemPaidByField.getSelectionModel().selectFirst();
		newItemToPayField.getSelectionModel().selectFirst();
		newItemTitleField.requestFocus();
	}

	/**
	 * Respond to cell event to change the item title.
	 * 
	 * @param event
	 *            cell event
	 */
	private void changeItemTitle(CellEditEvent<ItemModel, String> event) {
		ItemModel model = event.getRowValue();
		String newTitle = event.getNewValue();
		if (newTitle.isEmpty()) {
			// Enforce Redraw
			event.getTableColumn().setVisible(false);
			event.getTableColumn().setVisible(true);
			return;
		} else {
			model.titleProperty().set(newTitle);
		}
		changeItem(model);
	}

	/**
	 * Respond to cell event to change the item price.
	 * 
	 * @param event
	 *            cell event
	 */
	private void changeItemPrice(CellEditEvent<ItemModel, String> event) {
		ItemModel model = event.getRowValue();
		Long newPrice = resolvePrice(event.getNewValue());

		if (newPrice == null) {
			// Enforce Redraw
			event.getTableColumn().setVisible(false);
			event.getTableColumn().setVisible(true);
			return;
		} else {
			model.priceProperty().set(newPrice);
		}
		changeItem(model);
	}

	/**
	 * Respond to cell event to change the item quantity.
	 * 
	 * @param event
	 *            cell event
	 */
	private void changeItemQuantity(CellEditEvent<ItemModel, String> event) {
		ItemModel model = event.getRowValue();
		Integer newQuantity = resolveQuantity(event.getNewValue());
		if (newQuantity == null) {
			// Enforce Redraw
			event.getTableColumn().setVisible(false);
			event.getTableColumn().setVisible(true);
			return;
		} else {
			model.quantityProperty().set(newQuantity);
		}
		changeItem(model);
	}

	/**
	 * Respond to cell event to change the item "paid by" person.
	 * 
	 * @param event
	 *            cell event
	 */
	private void changeItemPaidBy(CellEditEvent<ItemModel, PersonListModel> event) {
		ItemModel model = event.getRowValue();
		PersonListModel newPaidBy = event.getNewValue();
		model.paidbyProperty().set(newPaidBy.getId());
		changeItem(model);
	}

	/**
	 * Respond to cell event to change the item "to pay" model.
	 * 
	 * @param event
	 *            cell event
	 */
	private void changeItemToPay(CellEditEvent<ItemModel, ToPayModel> event) {
		ItemModel model = event.getRowValue();
		ToPayModel newToPay = event.getNewValue();
		switch (newToPay.getType()) {
		case All:
			model.personToPayProperty().set(0);
			model.groupToPayProperty().set(0);
			break;

		case Person:
			model.personToPayProperty().set(newToPay.getPerson().getId());
			model.groupToPayProperty().set(0);
			break;

		case Group:
			model.personToPayProperty().set(0);
			model.groupToPayProperty().set(newToPay.getGroup().getId());
			break;

		default:
			break;
		}
		changeItem(model);
	}

	/**
	 * Change item with updated model.
	 * 
	 * @param model
	 *            item model to change
	 */
	private void changeItem(ItemModel model) {
		getApp().changeItem(model.getId(), model.titleProperty().get(), model.priceProperty().get(),
				model.quantityProperty().get(), zeroToNull(model.paidbyProperty().get()),
				zeroToNull(model.personToPayProperty().get()), zeroToNull(model.groupToPayProperty().get()));
	}

	/**
	 * Delete selected item.
	 */
	@FXML
	public void deletetem() {
		ItemModel selectedModel = itemTable.selectionModelProperty().get().selectedItemProperty().get();
		if (selectedModel != null) {
			getApp().deleteItem(selectedModel.getId());
		}
	}

	/**
	 * Event handler: Key Pressed on field in "create invoice" form group.
	 * 
	 * @param event
	 *            key event
	 */
	@FXML
	public void onCreateInvoiceFieldKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			if (!addInvoiceButton.isDisabled()) {
				addNewInvoice();
			}
			return;
		}
	}

	/**
	 * Event handler: Key Pressed on invoices table.
	 * 
	 * @param event
	 *            key event
	 */
	@FXML
	public void onInvoiceTableKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.DELETE) {
			deleteInvoice();
			return;
		}
		if (event.getCode() == KeyCode.ESCAPE) {
			invoicesTable.getSelectionModel().clearSelection();
			newInvoiceTitleField.requestFocus();
			return;
		}
	}

	/**
	 * Event handler: Key Pressed on field in "create item" form group.
	 * 
	 * @param event
	 *            key event
	 */
	@FXML
	public void onCreateItemFieldKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			if (!addItemButton.isDisabled()) {
				addNewItem();
			}
			return;
		}
	}

	/**
	 * Event handler: Key Pressed on items table.
	 * 
	 * @param event
	 *            key event
	 */
	@FXML
	public void onItemTableKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.DELETE) {
			deletetem();
			return;
		}
		if (event.getCode() == KeyCode.ESCAPE) {
			itemTable.getSelectionModel().clearSelection();
			newItemTitleField.requestFocus();
			return;
		}
	}
}
