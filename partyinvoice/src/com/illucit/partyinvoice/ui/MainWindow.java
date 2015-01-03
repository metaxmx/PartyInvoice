package com.illucit.partyinvoice.ui;

import static javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.illucit.partyinvoice.data.Person;
import com.illucit.partyinvoice.data.Project;
import com.illucit.partyinvoice.ui.model.PersonModel;
import com.illucit.partyinvoice.ui.view.AboutController;
import com.illucit.partyinvoice.ui.view.MainWindowController;
import com.illucit.partyinvoice.ui.view.MenuController;
import com.illucit.partyinvoice.ui.view.MessageController;
import com.illucit.partyinvoice.ui.view.SaveConfirmController;
import com.illucit.partyinvoice.ui.view.SaveConfirmController.ConfirmResult;

public class MainWindow extends Application {

	private static final String BUNDLE_NAME = "partyinvoice";

	private Locale locale = Locale.getDefault();
	private ResourceBundle bundle;

	private Stage primaryStage;
	private Stage aboutStage;
	private Stage messageStage;

	private BorderPane rootLayout;

	private File xmlFile = null;
	private Project project;
	private boolean changed = true;

	private ObservableList<PersonModel> personData = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) throws Exception {

		loadResourceBundle();

		this.primaryStage = primaryStage;
		this.primaryStage.setOnCloseRequest(event -> {
			AtomicBoolean consumed = new AtomicBoolean(true);
			saveConfirm(() -> {
				consumed.set(false);
			});
			if (consumed.get()) {
				System.out.println("Consumed");
				event.consume();
			}
		});
		this.primaryStage.setTitle(bundle.getString("ui.title"));
		this.primaryStage.getIcons().add(new Image("icons/icon-16.png"));
		this.primaryStage.getIcons().add(new Image("icons/icon-24.png"));
		this.primaryStage.getIcons().add(new Image("icons/icon-32.png"));
		this.primaryStage.getIcons().add(new Image("icons/icon-48.png"));

		initRootLayout();

		// Show the scene containing the root layout.
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();

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

		showMainView();
	}

	public void changeLocale(Locale locale) {

		this.locale = locale;
		System.out.println("Locale: " + locale);
		loadResourceBundle();

		initRootLayout();

		showMainView();

		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);

	}

	private void loadResourceBundle() {
		bundle = null;
		try {
			bundle = ResourceBundle.getBundle(BUNDLE_NAME, this.locale);
		} catch (MissingResourceException e) {
		}
		if (bundle == null) {
			// Fallback
			try {
				bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ENGLISH);
			} catch (MissingResourceException e) {
			}
		}
		if (bundle == null) {
			throw new IllegalStateException("Resource bundle " + BUNDLE_NAME + " was not found.");
		}
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(bundle);
			loader.setLocation(MainWindow.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();

			// Give the controller access to the main app.
			MenuController controller = loader.getController();
			controller.setMainWindow(this);

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
			loader.setResources(bundle);
			loader.setLocation(MainWindow.class.getResource("view/MainView.fxml"));
			AnchorPane personOverview = loader.load();

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

	public void quit() {
		primaryStage.fireEvent(new WindowEvent(primaryStage, WINDOW_CLOSE_REQUEST));
	}

	public ObservableList<PersonModel> getPersonData() {
		return personData;
	}

	public void showAboutDialog() {
		try {
			// Load About View
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(bundle);
			loader.setLocation(MainWindow.class.getResource("view/AboutView.fxml"));
			AnchorPane aboutView = loader.load();

			// Give the controller access to the main app.
			AboutController controller = loader.getController();
			controller.setMainWindow(this);

			aboutStage = new Stage();
			aboutStage.setTitle(bundle.getString("ui.about.windowtitle"));
			aboutStage.setScene(new Scene(aboutView));
			aboutStage.setResizable(false);
			aboutStage.initModality(Modality.WINDOW_MODAL);
			aboutStage.initOwner(primaryStage);
			aboutStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeAboutDialog() {
		aboutStage.close();
	}

	public void followIllucitHyperlink() {
		getHostServices().showDocument("https://www.illucit.com");
	}

	public void menuNewProject() {
		saveConfirm(() -> {
			newProject();
		});
	}

	public void menuLoadProject() {
		saveConfirm(() -> {
			loadProject();
		});
	}

	public void showMessage(String messageTitleKey, String messageKey) {
		String title = bundle.getString(messageTitleKey);
		String message = bundle.getString(messageKey);
		try {
			// Load Message View
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(bundle);
			loader.setLocation(MainWindow.class.getResource("view/MessageView.fxml"));
			VBox messageView = loader.load();

			// Give the controller access to the main app.
			MessageController controller = loader.getController();
			controller.setMessage(message);
			controller.setMainWindow(this);

			messageStage = new Stage();
			messageStage.setTitle(title);
			messageStage.setScene(new Scene(messageView));
			messageStage.setResizable(false);
			messageStage.initModality(Modality.WINDOW_MODAL);
			messageStage.initOwner(primaryStage);
			messageStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeMessage() {
		messageStage.close();
	}

	private void saveConfirm(Runnable callback) {
		if (!this.changed) {
			// No confirmation needed
			callback.run();
			return;
		}

		try {
			// Load Confirm View
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(bundle);
			loader.setLocation(MainWindow.class.getResource("view/SaveConfirmView.fxml"));
			VBox confirmView = (VBox) loader.load();

			// Give the controller access to the main app.
			SaveConfirmController controller = loader.getController();

			Stage confirmStage = new Stage();

			controller.setCallback(result -> {
				confirmStage.close();
				if (result == ConfirmResult.DISCARD) {
					// Discard values - continue with action
					callback.run();
				} else if (result == ConfirmResult.SAVE) {
					// Try to save and continue is save was successful
					boolean saved = saveProject();
					if (saved) {
						callback.run();
					}
				}
			});

			confirmStage.setTitle(bundle.getString("ui.saveconfirm.title"));
			confirmStage.setScene(new Scene(confirmView));
			confirmStage.setResizable(false);
			confirmStage.initModality(Modality.WINDOW_MODAL);
			confirmStage.initOwner(primaryStage);
			confirmStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			callback.run();
		}
	}

	private void updateProjectAfterLoad() {
		// TODO: Update Lists etc.
	}

	public void newProject() {
		this.project = new Project();
		this.xmlFile = null;
		this.changed = false;

		updateProjectAfterLoad();
	}

	private void loadProject() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(bundle.getString("ui.load.title"));
		File loadFile = fileChooser.showOpenDialog(primaryStage);

		if (loadFile == null) {
			return;
		}

		try {
			this.project = loadFromFile(loadFile);
			this.xmlFile = loadFile;
			this.changed = false;

			updateProjectAfterLoad();

		} catch (JAXBException e) {
			e.printStackTrace();
			showMessage("ui.message.errorload.title", "ui.message.errorload.text");
		}
	}

	public boolean saveProject() {
		if (this.xmlFile == null) {
			return saveAsProject();
		}

		try {
			saveToFile(this.xmlFile, project);
			this.changed = false;
			return true;
		} catch (JAXBException e) {
			e.printStackTrace();
			showMessage("ui.message.errorsave.title", "ui.message.errorsave.text");
		}
		return false;
	}

	public boolean saveAsProject() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(bundle.getString("ui.saveas.title"));
		File saveFile = fileChooser.showSaveDialog(primaryStage);

		if (saveFile == null) {
			return false;
		}

		try {
			saveToFile(saveFile, project);
			this.xmlFile = saveFile;
			this.changed = false;
			return true;
		} catch (JAXBException e) {
			e.printStackTrace();
			showMessage("ui.message.errorsave.title", "ui.message.errorsave.text");
		}
		return false;
	}

	private static void saveToFile(File targetFile, Project project) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Project.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(project, targetFile);
	}

	private static Project loadFromFile(File targetFile) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Project.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (Project) unmarshaller.unmarshal(targetFile);
	}

}
