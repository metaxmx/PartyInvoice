package com.illucit.partyinvoice.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import com.illucit.partyinvoice.ui.MainWindow;
import com.illucit.partyinvoice.ui.model.PersonModel;

public class MainWindowController {

	@FXML
	private AnchorPane rightSidePane;
	
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
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		System.out.println(personList);
		personList.setItems(mainWindow.getPersonData());
	}

}
