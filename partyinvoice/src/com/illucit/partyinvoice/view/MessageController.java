package com.illucit.partyinvoice.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for Message View.
 * 
 * @author Christian Simon
 *
 */
public class MessageController {

	@FXML
	private Label label;

	private Stage stage;

	public void setMessage(String msg) {
		label.setText(msg);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void close() {
		stage.close();
	}

}
