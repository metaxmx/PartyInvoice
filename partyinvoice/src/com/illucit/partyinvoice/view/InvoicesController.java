package com.illucit.partyinvoice.view;

import static com.illucit.partyinvoice.CurrencyUtil.currencyToString;
import static com.illucit.partyinvoice.ExtendedBindings.conditionBinding;
import static com.illucit.partyinvoice.ExtendedBindings.resolvingBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import com.illucit.partyinvoice.AbstractController;
import com.illucit.partyinvoice.PartyInvoiceApp;
import com.illucit.partyinvoice.model.InvoiceModel;
import com.illucit.partyinvoice.model.ItemModel;
import com.illucit.partyinvoice.model.PersonModel;
import com.illucit.partyinvoice.model.ToPayModel;
import com.illucit.partyinvoice.model.ToPayModel.ToPayType;

public class InvoicesController extends AbstractController {

	/*
	 * Invoice
	 */

	@FXML
	private TableView<InvoiceModel> invoicesTable;

	@FXML
	private TableColumn<InvoiceModel, String> invoiceTitleCol;

	@FXML
	private TableColumn<InvoiceModel, String> invoicePaidByCol;

	@FXML
	private TableColumn<InvoiceModel, String> invoiceItemsCol;

	@FXML
	private TableColumn<InvoiceModel, String> invoiceTotalCol;

	@FXML
	private TextField newInvoiceTitleField;

	@FXML
	private ChoiceBox<PersonModel> newInvoicePaidByField;

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
	private TableColumn<ItemModel, Integer> itemQuantityCol;

	@FXML
	private TableColumn<ItemModel, String> itemTotalCol;

	@FXML
	private TableColumn<ItemModel, String> itemPaidByCol;

	@FXML
	private TableColumn<ItemModel, String> itemToPayCol;

	@FXML
	private TextField newItemTitleField;

	@FXML
	private TextField newItemPriceField;

	@FXML
	private TextField newItemQuantityField;

	@FXML
	private TextField newItemTotalField;

	@FXML
	private ChoiceBox<PersonModel> newItemPaidByField;

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
		invoicePaidByCol.setCellValueFactory(new PropertyValueFactory<>("paidByName"));
		invoiceItemsCol.setCellValueFactory(new PropertyValueFactory<>("items"));
		invoiceTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

		invoicesTable.setItems(app.getInvoiceList());

		newInvoicePaidByField.setItems(app.getPersonList());

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
		itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		itemQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		itemTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		itemPaidByCol.setCellValueFactory(new PropertyValueFactory<>("paidByName"));
		itemToPayCol.setCellValueFactory(new PropertyValueFactory<>("topay"));

		itemTable.setItems(app.getItemList());

		newItemPaidByField.setItems(app.getPersonList());

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

	private static boolean isPriceInvalid(String value) {
		return resolvePrice(value) == null;
	}

	private static boolean isQuantityInvalid(String value) {
		return resolveQuantity(value) == null;
	}

	private static String resolveTotal(String priceValue, String quantityValue) {
		long total = 0;
		Integer quantity = resolveQuantity(quantityValue);
		Long price = resolvePrice(priceValue);
		if (price != null && quantity != null) {
			total = price * quantity;
		}
		return currencyToString(total);
	}

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

	private static final Pattern pricePattern = Pattern.compile("^\\s*([0-9]*)([,.][0-9]{0,2})?(\\s*€?)?\\s*$");

	private static Long resolvePrice(String value) {
		Matcher priceMatcher = pricePattern.matcher(value);
		if (!priceMatcher.matches()) {
			return null;
		}
		long price = 0;
		String sect1 = priceMatcher.group(1);
		String sect2 = priceMatcher.group(2);
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
		return price;
	}

	@FXML
	public void addNewInvoice() {
		String newTitle = newInvoiceTitleField.getText();
		int newPaidBy = newInvoicePaidByField.getSelectionModel().getSelectedItem().getId();
		getApp().addInvoice(newTitle, newPaidBy);
		newInvoiceTitleField.textProperty().set("");
		newInvoicePaidByField.getSelectionModel().clearSelection();
	}

	@FXML
	public void deleteInvoice() {
		InvoiceModel selectedModel = invoicesTable.selectionModelProperty().get().selectedItemProperty().get();
		if (selectedModel != null) {
			getApp().deleteInvoice(selectedModel.getId());
		}
	}

	@FXML
	public void addNewItem() {
		String newTitle = newItemTitleField.getText();
		Integer quantity = resolveQuantity(newItemQuantityField.getText());
		Long price = resolvePrice(newItemPriceField.getText());
		Integer paidBy = null;
		if (!newItemPaidByField.getSelectionModel().isEmpty()) {
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
		newItemPaidByField.getSelectionModel().clearSelection();
		newItemToPayField.getSelectionModel().selectFirst();
	}

	@FXML
	public void deletetem() {
		ItemModel selectedModel = itemTable.selectionModelProperty().get().selectedItemProperty().get();
		if (selectedModel != null) {
			getApp().deleteItem(selectedModel.getId());
		}
	}
}
