package com.illucit.partyinvoice.view;

import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.illucit.partyinvoice.AbstractController;
import com.illucit.partyinvoice.PartyInvoiceApp;
import com.illucit.partyinvoice.model.ResultModel;

/**
 * Controller for "result" view.
 * 
 * @author Christian Simon
 *
 */
public class ResultController extends AbstractController {

	@FXML
	private TableView<ResultModel> resultTable;

	@FXML
	private TableColumn<ResultModel, String> nameCol;

	@FXML
	private TableColumn<ResultModel, String> transactionCol;

	@FXML
	private TableColumn<ResultModel, String> amountCol;

	@Override
	public void setApp(PartyInvoiceApp app) {
		super.setApp(app);

		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		transactionCol.setCellValueFactory(new PropertyValueFactory<>("transaction"));
		amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

		resultTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		resultTable.setItems(app.getResultList());

	}

}
