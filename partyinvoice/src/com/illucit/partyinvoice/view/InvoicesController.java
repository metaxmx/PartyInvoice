package com.illucit.partyinvoice.view;

import static com.google.common.base.Strings.isNullOrEmpty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import com.illucit.partyinvoice.AbstractController;
import com.illucit.partyinvoice.PartyInvoiceApp;
import com.illucit.partyinvoice.model.InvoiceModel;

public class InvoicesController extends AbstractController {

	/*
	 * Invoice
	 */
	
	@FXML
	private TableView<InvoiceModel> invoicesTable;

	@FXML
	private TableColumn<InvoiceModel, String> nameCol;

	@FXML
	private TableColumn<InvoiceModel, String> paidByCol;

	@FXML
	private TableColumn<InvoiceModel, String> itemsCol;

	@FXML
	private TableColumn<InvoiceModel, String> totalCol;

	@FXML
	private TextField newInvoiceField;

	@FXML
	private ChoiceBox<String> newPaidByField;

	@FXML
	private Button addInvoiceButton;

	@FXML
	private Button deleteInvoiceButton;

	/*
	 * Item
	 */
	
	@FXML
	private TableView<Object> itemTable;
	
	@Override
	public void setApp(PartyInvoiceApp app) {
		super.setApp(app);

		nameCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		paidByCol.setCellValueFactory(new PropertyValueFactory<>("paidBy"));
		itemsCol.setCellValueFactory(new PropertyValueFactory<>("items"));
		totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

		invoicesTable.setItems(app.getInvoiceList());

		newPaidByField.setItems(app.getPersonNameList());

		newInvoiceField.textProperty().addListener((observable, oldVal, newVal) -> {
			if (isNullOrEmpty(newVal) || newPaidByField.getSelectionModel().isEmpty()) {
				addInvoiceButton.disableProperty().set(true);
			} else {
				addInvoiceButton.disableProperty().set(false);
			}
		});
		newPaidByField.selectionModelProperty().get().selectedItemProperty()
				.addListener(((observable, oldVal, newVal) -> {
					if (isNullOrEmpty(newVal) || isNullOrEmpty(newInvoiceField.getText())) {
						addInvoiceButton.disableProperty().set(true);
					} else {
						addInvoiceButton.disableProperty().set(false);
					}
				}));

		invoicesTable.selectionModelProperty().get().selectedItemProperty()
				.addListener((observable, oldVal, newVal) -> {
					if (newVal == null) {
						deleteInvoiceButton.disableProperty().set(true);
					} else {
						deleteInvoiceButton.disableProperty().set(false);
					}
				});
	}

	public void addNewInvoice() {
		String newTitle = newInvoiceField.textProperty().get();
		String newPaidBy = newPaidByField.getSelectionModel().getSelectedItem();
		getApp().addInvoice(newTitle, newPaidBy);
		newInvoiceField.textProperty().set("");
		newPaidByField.getSelectionModel().clearSelection();
	}

	public void deleteInvoice() {
		InvoiceModel selectedModel = invoicesTable.selectionModelProperty().get().selectedItemProperty().get();
		if (selectedModel != null) {
			getApp().deleteInvoice(selectedModel.getTitle());
		}
	}
}
