package com.illucit.partyinvoice.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import com.illucit.partyinvoice.AbstractController;
import com.illucit.partyinvoice.PartyInvoiceApp;
import com.illucit.partyinvoice.model.PersonModel;

public class PersonController extends AbstractController {

	@FXML
	private TableView<PersonModel> personTable;

	@FXML
	private TableColumn<PersonModel, String> nameCol;

	@FXML
	private TableColumn<PersonModel, String> paidCol;

	@FXML
	private TableColumn<PersonModel, String> shareCol;

	@FXML
	private TableColumn<PersonModel, String> diffCol;

	@FXML
	private TextField newPersonField;

	@FXML
	private Button addPersonButton;

	@FXML
	private Button deletePersonButton;

	@Override
	public void setApp(PartyInvoiceApp app) {
		super.setApp(app);

		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		paidCol.setCellValueFactory(new PropertyValueFactory<>("paid"));
		shareCol.setCellValueFactory(new PropertyValueFactory<>("share"));
		diffCol.setCellValueFactory(new PropertyValueFactory<>("difference"));

		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setOnEditCommit(this::editPersonNameEvent);

		personTable.setItems(app.getPersonList());

		addPersonButton.disableProperty().bind(newPersonField.textProperty().isEmpty());
		deletePersonButton.disableProperty().bind(personTable.getSelectionModel().selectedItemProperty().isNull());

	}

	@FXML
	public void addNewPerson() {
		String newName = newPersonField.textProperty().get();
		getApp().addPerson(newName);
		newPersonField.textProperty().set("");
	}

	private void editPersonNameEvent(CellEditEvent<PersonModel, String> event) {
		PersonModel model = event.getRowValue();
		String newName = event.getNewValue();
		if (newName.isEmpty()) {
			// Enforce Redraw
			event.getTableColumn().setVisible(false);
			event.getTableColumn().setVisible(true);
			return;
		} else {
			model.nameProperty().set(newName);
		}
		getApp().changePerson(model.getId(), model.nameProperty().get());
	}

	@FXML
	public void deletePerson() {
		PersonModel selectedModel = personTable.selectionModelProperty().get().selectedItemProperty().get();
		if (selectedModel != null) {
			getApp().deletePerson(selectedModel.getId());
		}
	}

	@FXML
	public void onCreateFieldKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			if (!addPersonButton.isDisabled()) {
				addNewPerson();
			}
			return;
		}
	}

	@FXML
	public void onTableKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.DELETE) {
			deletePerson();
			return;
		}
		if (event.getCode() == KeyCode.ESCAPE) {
			personTable.getSelectionModel().clearSelection();
			newPersonField.requestFocus();
			return;
		}
	}

}
