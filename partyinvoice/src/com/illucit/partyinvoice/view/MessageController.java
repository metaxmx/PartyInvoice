package com.illucit.partyinvoice.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.illucit.partyinvoice.AbstractController;

/**
 * Controller for Message View.
 * 
 * @author Christian Simon
 *
 */
public class MessageController extends AbstractController {

	@FXML
	private Label label;

	public void setMessage(String msg) {
		label.setText(msg);
	}

	public void close() {
		getStage().close();
	}

}
