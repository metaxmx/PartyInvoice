package com.illucit.partyinvoice;

import javafx.stage.Stage;

/**
 * Abstract class for all controllers to store the app.
 * 
 * @author Christian Simon
 *
 */
public abstract class AbstractController {

	private PartyInvoiceApp app;

	private Stage stage;

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
	 * Set the stage of the controller.
	 * 
	 * @param stage
	 *            stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Get the app.
	 * 
	 * @return party invoice app
	 */
	public PartyInvoiceApp getApp() {
		return app;
	}

	/**
	 * Get the stage.
	 * 
	 * @return stage
	 */
	public Stage getStage() {
		return stage;
	}

}
