package com.illucit.partyinvoice.view;

import static com.google.common.base.Strings.isNullOrEmpty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

		personTable.setItems(app.getPersonList());

		newPersonField.textProperty().addListener((observable, oldVal, newVal) -> {
			if (isNullOrEmpty(newVal)) {
				addPersonButton.disableProperty().set(true);
			} else {
				addPersonButton.disableProperty().set(false);
			}
		});

		personTable.selectionModelProperty().get().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
			if (newVal == null) {
				deletePersonButton.disableProperty().set(true);
			} else {
				deletePersonButton.disableProperty().set(false);
			}
		});
	}

	public void addNewPerson() {
		String newName = newPersonField.textProperty().get();
		getApp().addPerson(newName);
		newPersonField.textProperty().set("");
	}

	public void deletePerson() {
		PersonModel selectedModel = personTable.selectionModelProperty().get().selectedItemProperty().get();
		if (selectedModel != null) {
			getApp().deletePerson(selectedModel.getName());
		}
	}

}
