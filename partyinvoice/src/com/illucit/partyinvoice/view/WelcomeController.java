package com.illucit.partyinvoice.view;

import com.illucit.partyinvoice.AbstractController;
import com.illucit.partyinvoice.PartyInvoiceApp.SelectedView;

/**
 * Controller for Welcome View.
 * 
 * @author Christian Simon
 *
 */
public class WelcomeController extends AbstractController {

	public void editPersons() {
		getApp().selectView(SelectedView.Persons);
	}

	public void editInvoices() {
		getApp().selectView(SelectedView.Invoices);
	}

	public void editResult() {
		getApp().selectView(SelectedView.Result);
	}

}
