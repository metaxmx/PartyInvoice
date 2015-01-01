package com.illucit.partyinvoice.ui;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.illucit.partyinvoice.data.Person;
import com.illucit.partyinvoice.data.Project;
import com.illucit.partyinvoice.ui.model.PersonModel;
import com.illucit.partyinvoice.ui.view.MainWindowController;

public class MainWindow extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private Project project;

	private ObservableList<PersonModel> personData = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");

		initRootLayout();

		this.project = new Project();

		Person p1 = new Person();
		p1.setName("Hannes");
		project.getPersons().add(p1);

		Person p2 = new Person();
		p2.setName("Udo");
		project.getPersons().add(p2);

		Person p3 = new Person();
		p3.setName("Fritz");
		p3.setDifference(-2345l);
		project.getPersons().add(p3);
		
		project.assign();
		project.calculate();

		refreshPersonList();
		
		System.out.println(personData);
		
		showMainView();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainWindow.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the main view.
	 */
	public void showMainView() {
		try {
			// Load Main View
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainWindow.class.getResource("view/MainView.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(personOverview);

			// Give the controller access to the main app.
			MainWindowController controller = loader.getController();
			controller.setMainWindow(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return main stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void refreshPersonList() {
		personData.clear();
		project.getPersons().stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).map(PersonModel::new)
				.forEach(personData::add);
	}

	public ObservableList<PersonModel> getPersonData() {
		return personData;
	}

}
