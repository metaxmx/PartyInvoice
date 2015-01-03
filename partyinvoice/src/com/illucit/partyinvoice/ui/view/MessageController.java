package com.illucit.partyinvoice.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.illucit.partyinvoice.ui.MainWindow;

public class MessageController {

	@FXML
	private Label label;

	private MainWindow mainWindow;

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void setMessage(String msg) {
		label.setText(msg);
	}

	public void close() {
		mainWindow.closeMessage();
	}

}
