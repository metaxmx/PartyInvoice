package com.illucit.partyinvoice.view;

import javafx.fxml.FXML;

import com.illucit.partyinvoice.AbstractController;

/**
 * Controller for the "About" dialog.
 * 
 * @author Christian Simon
 *
 */
public class AboutController extends AbstractController {

	/**
	 * Close about dialog.
	 */
	@FXML
	public void close() {
		getStage().close();
	}

	/**
	 * Follow vendor hyperlink.
	 */
	@FXML
	public void followHyperlink() {
		getApp().followIllucitHyperlink();
	}

}
