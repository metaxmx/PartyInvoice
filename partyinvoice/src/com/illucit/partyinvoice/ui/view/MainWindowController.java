package com.illucit.partyinvoice.ui.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import com.illucit.partyinvoice.ui.MainWindow;
import com.illucit.partyinvoice.ui.model.PersonModel;

public class MainWindowController {

	public enum SelectedAccordionTab {
		Welcome, Persons, Invoices, Result
	}
	
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

	private MainWindow mainWindow;

	@FXML
	public void initialize() {
		personList.setCellFactory(lv -> new ListCell<PersonModel>() {
			@Override
			protected void updateItem(PersonModel item, boolean empty) {
				super.updateItem(item, empty);
				if (item != null) {
					if (item.getPerson().getDifference() == null) {
						setText(item.getPerson().getName());
					} else {
						setText(item.getPerson().getName() + ": " + item.getPerson().getDifferenceStr());
					}
				}
			}
		});

		leftSideAccodion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
			public void changed(ObservableValue<? extends TitledPane> ov, TitledPane old_val, TitledPane new_val) {
				if (new_val != null) {
					System.out.println(">>> " + new_val.getText());
				} else {
					System.out.println(">>> Empty");
				}
			}
		});
		
		leftSideAccodion.
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		System.out.println(personList);
		personList.setItems(mainWindow.getPersonData());
	}

	public void updateRightSide(Node rightside) {
		rightSidePane.setCenter(rightside);
	}

	public void selectAccordionEmpty() {
		leftSideAccodion.expandedPaneProperty().setValue(null);
	}

}
