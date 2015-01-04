package com.illucit.partyinvoice;

import static com.illucit.partyinvoice.LogSetup.setupLogging;
import static com.illucit.partyinvoice.XmlIO.loadFromFile;
import static com.illucit.partyinvoice.XmlIO.saveToFile;
import static javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.illucit.partyinvoice.model.PersonModel;
import com.illucit.partyinvoice.view.AboutController;
import com.illucit.partyinvoice.view.MainController;
import com.illucit.partyinvoice.view.RootController;
import com.illucit.partyinvoice.xmldata.Person;
import com.illucit.partyinvoice.xmldata.Project;

/**
 * Main Application class for Party Invoice.
 * 
 * @author Christian Simon
 *
 */
public class PartyInvoiceApp extends Application {

	private static final String BUNDLE_NAME = "partyinvoice";

	public static final String VIEW_MESSAGE = "MessageView.fxml";
	public static final String VIEW_SAVECONFIRM = "SaveConfirmView.fxml";

	private File userSettingsDir;

	private Logger logger = LoggerFactory.getLogger(PartyInvoiceApp.class.getName());

	private Locale locale = Locale.getDefault();
	private ResourceBundle bundle;

	private Stage primaryStage;
	private Stage aboutStage;

	private BorderPane rootLayout;

	private Dialogs dialogs;

	private File xmlFile = null;
	
	private Project project;
	
	private boolean changed = true;
	
	private ObjectProperty<Project> projectProperty;

	private ObservableList<PersonModel> personData = FXCollections.observableArrayList();

	public static void main(String[] args) {
		PartyInvoiceApp.launch(PartyInvoiceApp.class, args);
	}

	@Override
	public void init() throws Exception {
		super.init();

		// Initialize Appication Settings directory
		String userDir = System.getenv("APPDATA"); // Windows
		if (userDir == null) {
			userDir = System.getProperty("user.home", "."); // Linux fallback
		}
		userSettingsDir = new File(userDir, ".partyinvoice");
		if (!userSettingsDir.exists()) {
			userSettingsDir.mkdirs();
		}

		setupLogging(userSettingsDir);

		logger.info("Starting PartyInvoice 1.0 ...");

		dialogs = new Dialogs(this);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		loadResourceBundle();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(bundle.getString("ui.title"));
		this.primaryStage.getIcons().add(new Image("icons/icon-16.png"));
		this.primaryStage.getIcons().add(new Image("icons/icon-24.png"));
		this.primaryStage.getIcons().add(new Image("icons/icon-32.png"));
		this.primaryStage.getIcons().add(new Image("icons/icon-48.png"));
		this.primaryStage.setOnCloseRequest(event -> {
			AtomicBoolean consumed = new AtomicBoolean(true);
			dialogs.saveConfirm(() -> {
				consumed.set(false);
			});
			if (consumed.get()) {
				event.consume();
				return;
			}
			logger.info("PartyInvoice Stopped.");
		});

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

	public ResourceBundle getBundle() {
		return bundle;
	}

	public boolean isChanged() {
		return changed;
	}
	
	public ObjectProperty<Project> getProjectProperty() {
		return projectProperty;
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(bundle);
			loader.setLocation(PartyInvoiceApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();

			// Give the controller access to the main app.
			RootController controller = loader.getController();
			controller.setApp(this);

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
			loader.setLocation(PartyInvoiceApp.class.getResource("view/MainView.fxml"));
			AnchorPane personOverview = loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(personOverview);

			// Give the controller access to the main app.
			MainController controller = loader.getController();
			controller.setApp(this);
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
			loader.setLocation(PartyInvoiceApp.class.getResource("view/AboutView.fxml"));
			AnchorPane aboutView = loader.load();

			// Give the controller access to the main app.
			AboutController controller = loader.getController();
			controller.setApp(this);

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
		dialogs.saveConfirm(() -> {
			newProject();
		});
	}

	public void menuLoadProject() {
		dialogs.saveConfirm(() -> {
			loadProject();
		});
	}

	private void updateProjectAfterSourceChange() {
		// TODO: Update Lists etc.
	}

	public void newProject() {
		this.project = new Project();
		this.xmlFile = null;
		this.changed = false;

		logger.info("Creating new project");

		updateProjectAfterSourceChange();
	}

	private void loadProject() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(bundle.getString("ui.load.title"));
		File loadFile = fileChooser.showOpenDialog(primaryStage);

		if (loadFile == null) {
			return;
		}

		logger.info("Loading project from file {}", loadFile.getPath());

		try {
			this.project = loadFromFile(loadFile);
			this.xmlFile = loadFile;
			this.changed = false;

			updateProjectAfterSourceChange();

		} catch (JAXBException e) {
			logger.error("Error loading project", e);
			dialogs.showMessage("ui.message.errorload.title", "ui.message.errorload.text");
		}
	}

	public boolean saveProject() {
		if (this.xmlFile == null) {
			return saveAsProject();
		}

		logger.info("Saving project to {}", this.xmlFile.getPath());

		try {
			saveToFile(this.xmlFile, project);
			this.changed = false;
			return true;
		} catch (JAXBException e) {
			logger.error("Error saving project", e);
			dialogs.showMessage("ui.message.errorsave.title", "ui.message.errorsave.text");
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

		logger.info("Saving project to file {}", saveFile.getPath());

		try {
			saveToFile(saveFile, project);
			this.xmlFile = saveFile;
			this.changed = false;
			return true;
		} catch (JAXBException e) {
			logger.error("Error saving project", e);
			dialogs.showMessage("ui.message.errorsave.title", "ui.message.errorsave.text");
		}
		return false;
	}

}
