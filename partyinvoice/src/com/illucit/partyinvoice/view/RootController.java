package com.illucit.partyinvoice.view;

import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

import com.illucit.partyinvoice.AbstractController;
import com.illucit.partyinvoice.PartyInvoiceApp.SelectedView;
import com.illucit.partyinvoice.model.PersonModel;

/**
 * Controller for Root Layout (especially the Menu bar).
 * 
 * @author Christian Simon
 *
 */
public class RootController extends AbstractController {

	@FXML
	private MenuItem menuUndo;

	@FXML
	private MenuItem menuRedo;

	@FXML
	private BorderPane rightSidePane;

	@FXML
	private Hyperlink welcomeLink;

	@FXML
	private Hyperlink personsLink;

	@FXML
	private Hyperlink invoiceLink;

	@FXML
	private Hyperlink resultLink;

	@FXML
	private ListView<PersonModel> personList;

	@FXML
	private Button addPersonButton;

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
		getApp().undo();
	}

	public void editRedo() {
		getApp().redo();
	}

	public void showWelcome() {
		getApp().selectView(SelectedView.Welcome);
	}

	public void editPersons() {
		getApp().selectView(SelectedView.Persons);
	}

	public void editInvoices() {
		getApp().selectView(SelectedView.Invoices);
	}

	public void editResult() {
		getApp().selectView(SelectedView.Result);
	}

	public void setUndoEnabled(boolean enabled) {
		menuUndo.disableProperty().setValue(!enabled);
	}

	public void setRedoEnabled(boolean enabled) {
		menuRedo.disableProperty().setValue(!enabled);
	}

	public void updateRightSide(Node rightside) {
		rightSidePane.setCenter(rightside);
	}

	public void highlightLink() {
		switch (getApp().getView()) {
		case Welcome:
			welcomeLink.setVisited(true);
			personsLink.setVisited(false);
			invoiceLink.setVisited(false);
			resultLink.setVisited(false);
			break;

		case Persons:
			welcomeLink.setVisited(false);
			personsLink.setVisited(true);
			invoiceLink.setVisited(false);
			resultLink.setVisited(false);
			break;

		case Invoices:
			welcomeLink.setVisited(false);
			personsLink.setVisited(false);
			invoiceLink.setVisited(true);
			resultLink.setVisited(false);
			break;

		case Result:
			welcomeLink.setVisited(false);
			personsLink.setVisited(false);
			invoiceLink.setVisited(false);
			resultLink.setVisited(true);
			break;

		default:
			break;
		}
	}

}
