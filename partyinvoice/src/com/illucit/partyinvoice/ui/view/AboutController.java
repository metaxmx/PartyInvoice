package com.illucit.partyinvoice.ui.view;

import javafx.fxml.FXML;

import com.illucit.partyinvoice.ui.MainWindow;

public class AboutController {

	private MainWindow mainWindow;
	
	@FXML
	public void initialize() {
		
	}
	
	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public void close() {
		mainWindow.closeAboutDialog();
	}
	
	public void followHyperlink() {
		mainWindow.followIllucitHyperlink();
	}
	
}
