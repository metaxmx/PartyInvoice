package com.illucit.partyinvoice.view;

import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import com.illucit.partyinvoice.AbstractController;

/**
 * Controller for Root Layout (especially the Menu bar).
 * 
 * @author Christian Simon
 *
 */
public class RootController extends AbstractController {

	@FXML
	private MenuItem editUndo;

	@FXML
	private MenuItem editRedo;

	public void changeLocaleGerman() {
		getApp().changeLocale(Locale.GERMAN);
	}

	public void changeLocaleEnglish() {
		getApp().changeLocale(Locale.ENGLISH);
	}

	public void newProject() {
		getApp().menuNewProject();
	}

	public void loadProject() {
		getApp().menuLoadProject();
	}

	public void saveProject() {
		getApp().saveProject();
	}

	public void saveProjectAs() {
		getApp().saveAsProject();
	}

	public void about() {
		getApp().showAboutDialog();
	}

	public void quit() {
		getApp().quit();
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
