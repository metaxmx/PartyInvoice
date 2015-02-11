package com.illucit.partyinvoice.view;

import javafx.fxml.FXML;

import com.illucit.partyinvoice.AbstractController;
import com.illucit.partyinvoice.PartyInvoiceApp.SelectedView;

/**
 * Controller for Welcome View.
 * 
 * @author Christian Simon
 *
 */
public class WelcomeController extends AbstractController {

	/**
	 * Show "persons" view.
	 */
	@FXML
	public void editPersons() {
		getApp().selectView(SelectedView.Persons);
	}

	/**
	 * Show "invoices" view.
	 */
	@FXML
	public void editInvoices() {
		getApp().selectView(SelectedView.Invoices);
	}

	/**
	 * Show "result" view.
	 */
	@FXML
	public void editResult() {
		getApp().selectView(SelectedView.Result);
	}

}
