package com.illucit.partyinvoice;


/**
 * Abstract class for all controllers to store the app.
 * 
 * @author Christian Simon
 *
 */
public abstract class AbstractController {

	private PartyInvoiceApp app;

	/**
	 * Set the app.
	 * 
	 * @param app
	 *            party invoice app instance
	 */
	public void setApp(PartyInvoiceApp app) {
		this.app = app;
	}

	/**
	 * Get the app.
	 * 
	 * @return party invoice app
	 */
	public PartyInvoiceApp getApp() {
		return app;
	}

}
