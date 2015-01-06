package com.illucit.partyinvoice.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

	@Override
	public void setApp(PartyInvoiceApp app) {
		super.setApp(app);

		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		paidCol.setCellValueFactory(new PropertyValueFactory<>("paid"));
		shareCol.setCellValueFactory(new PropertyValueFactory<>("share"));
		diffCol.setCellValueFactory(new PropertyValueFactory<>("difference"));
		
		personTable.setItems(app.getPersonList());
	}

}
