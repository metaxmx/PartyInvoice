package com.illucit.partyinvoice.view;

import java.util.Locale;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

import com.illucit.partyinvoice.AbstractController;
import com.illucit.partyinvoice.model.PersonModel;

/**
 * Controller for Root Layout (especially the Menu bar).
 * 
 * @author Christian Simon
 *
 */
public class RootController extends AbstractController {

	public enum SelectedAccordionTab {
		Welcome, Persons, Invoices, Result
	}

	@FXML
	private MenuItem menuUndo;

	@FXML
	private MenuItem menuRedo;

	@FXML
	private BorderPane rightSidePane;

	@FXML
	private Accordion leftSideAccodion;

	@FXML
	private TitledPane personsAccordionPane;

	@FXML
	private TitledPane invoicesAccordionPane;

	@FXML
	private TitledPane resultAccordionPane;

	@FXML
	private TitledPane welcomeAccordionPane;

	@FXML
	private ListView<PersonModel> personList;

	@FXML
	private Button addPersonButton;

	@FXML
	public void initialize() {
		leftSideAccodion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
			public void changed(ObservableValue<? extends TitledPane> ov, TitledPane old_val, TitledPane new_val) {
				if (new_val != null) {
					System.out.println(">>> " + new_val.getText());
				} else {
					System.out.println(">>> Empty");
				}
			}
		});
	}

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

	public void editPersons() {
		System.out.println("Persons");
	}

	public void editInvoices() {
		System.out.println("Invoices");
	}

	public void editResult() {
		System.out.println("Result");
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

}
