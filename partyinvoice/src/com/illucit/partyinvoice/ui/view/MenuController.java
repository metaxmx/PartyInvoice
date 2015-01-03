package com.illucit.partyinvoice.ui.view;

import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import com.illucit.partyinvoice.ui.MainWindow;

public class MenuController {

	@FXML
	private MenuItem fileNew;

	@FXML
	private MenuItem fileLoad;

	@FXML
	private MenuItem fileSave;

	@FXML
	private MenuItem fileSaveAs;

	@FXML
	private MenuItem fileQuit;

	@FXML
	private MenuItem languageEn;

	@FXML
	private MenuItem languageDe;

	@FXML
	private MenuItem helpAbout;

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

}
