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

	/**
	 * Menu: Change language to "German".
	 */
	@FXML
	public void changeLocaleGerman() {
		getApp().changeLocale(Locale.GERMAN);
	}

	/**
	 * Menu: Change language to "German".
	 */
	@FXML
	public void changeLocaleEnglish() {
		getApp().changeLocale(Locale.ENGLISH);
	}

	/**
	 * Menu: Start new project.
	 */
	@FXML
	public void newProject() {
		getApp().menuNewProject();
	}

	/**
	 * Menu: Load project.
	 */
	@FXML
	public void loadProject() {
		getApp().menuLoadProject();
	}

	/**
	 * Menu: Save project.
	 */
	@FXML
	public void saveProject() {
		getApp().saveProject();
	}

	/**
	 * Menu: Save project as ...
	 */
	@FXML
	public void saveProjectAs() {
		getApp().saveAsProject();
	}

	/**
	 * Menu: Show about dialog.
	 */
	@FXML
	public void about() {
		getApp().showAboutDialog();
	}

	/**
	 * Menu: Quit program.
	 */
	@FXML
	public void quit() {
		getApp().quit();
	}

	/**
	 * Menu: Perform undo.
	 */
	@FXML
	public void editUndo() {
		getApp().undo();
	}

	/**
	 * Menu: Perform redo.
	 */
	@FXML
	public void editRedo() {
		getApp().redo();
	}

	/**
	 * Menu: Show "welcome" view.
	 */
	@FXML
	public void showWelcome() {
		getApp().selectView(SelectedView.Welcome);
	}

	/**
	 * Menu: Show "persons" view.
	 */
	@FXML
	public void editPersons() {
		getApp().selectView(SelectedView.Persons);
	}

	/**
	 * Menu: Show "invoices" view.
	 */
	@FXML
	public void editInvoices() {
		getApp().selectView(SelectedView.Invoices);
	}

	/**
	 * Menu: Show "result" view.
	 */
	@FXML
	public void editResult() {
		getApp().selectView(SelectedView.Result);
	}

	/**
	 * Change disabled proeprty of "Undo" menu entry.
	 * 
	 * @param enabled
	 *            ntrue if menu entry should be enabled
	 */
	public void setUndoEnabled(boolean enabled) {
		menuUndo.disableProperty().setValue(!enabled);
	}

	/**
	 * Change disabled proeprty of "Redo" menu entry.
	 * 
	 * @param enabled
	 *            ntrue if menu entry should be enabled
	 */
	public void setRedoEnabled(boolean enabled) {
		menuRedo.disableProperty().setValue(!enabled);
	}

	/**
	 * Refresh the view on the right side.
	 * 
	 * @param rightside
	 *            new view parent node
	 */
	public void updateRightSide(Node rightside) {
		rightSidePane.setCenter(rightside);
	}

	/**
	 * Highlight the links on the left side according to the selected right side
	 * view.
	 */
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
