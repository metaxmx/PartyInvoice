package com.illucit.partyinvoice.ui.view;

import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import com.illucit.partyinvoice.ui.MainWindow;

public class MenuController {

	@FXML
	private MenuItem editUndo;

	@FXML
	private MenuItem editRedo;

	private MainWindow mainWindow;

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void changeLocaleGerman() {
		mainWindow.changeLocale(Locale.GERMAN);
	}

	public void changeLocaleEnglish() {
		mainWindow.changeLocale(Locale.ENGLISH);
	}

	public void newProject() {
		mainWindow.menuNewProject();
	}

	public void loadProject() {
		mainWindow.menuLoadProject();
	}

	public void saveProject() {
		mainWindow.saveProject();
	}

	public void saveProjectAs() {
		mainWindow.saveAsProject();
	}

	public void about() {
		mainWindow.showAboutDialog();
	}

	public void quit() {
		mainWindow.quit();
	}

	public void editUndo() {
		System.out.println("Undo");
	}

	public void editRedo() {
		System.out.println("Redo");
	}

	public void editPersons() {
		System.out.println("Persons");
	}

	public void editInvoices() {
		System.out.println("Invoices");
	}

	public void editResult() {
		System.out.println("Result");
	}

}
