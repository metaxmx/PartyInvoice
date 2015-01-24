package com.illucit.partyinvoice;

import static com.illucit.partyinvoice.FxmlHelper.loadFxml;
import static com.illucit.partyinvoice.FxmlHelper.loadFxmlStage;
import static com.illucit.partyinvoice.LogSetup.setupLogging;
import static com.illucit.partyinvoice.XmlIO.loadFromFile;
import static com.illucit.partyinvoice.XmlIO.saveToFile;
import static java.util.stream.Collectors.toList;
import static javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST;

import java.io.File;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.illucit.partyinvoice.data.Operation;
import com.illucit.partyinvoice.immutabledata.ImmutablePerson;
import com.illucit.partyinvoice.immutabledata.ImmutableProjectHolder;
import com.illucit.partyinvoice.immutabledata.operation.AddInvoiceOp;
import com.illucit.partyinvoice.immutabledata.operation.AddPersonOp;
import com.illucit.partyinvoice.immutabledata.operation.DelInvoiceOp;
import com.illucit.partyinvoice.immutabledata.operation.DelPersonOp;
import com.illucit.partyinvoice.model.InvoiceModel;
import com.illucit.partyinvoice.model.PersonModel;
import com.illucit.partyinvoice.view.RootController;
import com.illucit.partyinvoice.xmldata.XmlProject;

/**
 * Main Application class for Party Invoice.
 * 
 * @author Christian Simon
 *
 */
public class PartyInvoiceApp extends Application {

	public enum SelectedView {
		Welcome, Persons, Invoices, Result
	}

	private static final String BUNDLE_NAME = "partyinvoice";

	public static final String VIEW_ROOT = "RootLayout.fxml";
	public static final String VIEW_MAIN = "MainLayout.fxml";
	public static final String VIEW_MESSAGE = "MessageView.fxml";
	public static final String VIEW_SAVECONFIRM = "SaveConfirmView.fxml";
	public static final String VIEW_ABOUT = "AboutView.fxml";

	public static final String VIEW_WELCOME = "WelcomeView.fxml";
	public static final String VIEW_PERSONS = "PersonView.fxml";
	public static final String VIEW_INVOICES = "InvoicesView.fxml";

	private File userSettingsDir;

	private Logger logger = LoggerFactory.getLogger(PartyInvoiceApp.class.getName());

	private Locale locale = Locale.getDefault();
	private ResourceBundle bundle;

	private Stage primaryStage;

	private BorderPane rootLayout;

	private RootController rootController;

	private Dialogs dialogs;

	private SelectedView view = SelectedView.Welcome;

	private ImmutableProjectHolder projectHolder;

	private File xmlFile = null;
	private boolean changed = false;

	private ObservableList<PersonModel> personList;

	private ObservableList<String> personNameList;

	private ObservableList<InvoiceModel> invoiceList;

	/**
	 * Start program.
	 * 
	 * @param args
	 *            parameters from commandline
	 */
	public static void main(String[] args) {
		PartyInvoiceApp.launch(PartyInvoiceApp.class, args);
	}

	/**
	 * Initialize JavaFX application.
	 */
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

	/**
	 * Start main window.
	 */
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

		personList = FXCollections.observableArrayList();
		personNameList = FXCollections.observableArrayList();
		invoiceList = FXCollections.observableArrayList();

		initializeProject();

		initUi();

		refreshObserableData();

		primaryStage.show();
	}

	/**
	 * Change locale and reload UI.
	 * 
	 * @param locale
	 *            target locale
	 */
	public void changeLocale(Locale locale) {

		this.locale = locale;
		System.out.println("Locale: " + locale);
		loadResourceBundle();

		initUi();
	}

	/**
	 * Initialize UI.
	 */
	private void initUi() {
		rootLayout = loadFxml(this, VIEW_ROOT, RootController.class, (controller, bundle) -> {
			rootController = controller;
		});

		selectView(SelectedView.Welcome);

		// Show the scene containing the root layout.
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
	}

	public void selectView(SelectedView view) {
		this.view = view;
		loadInnerView();
	}

	private void loadInnerView() {
		switch (view) {
		case Welcome:
			Parent welcome = loadFxml(this, VIEW_WELCOME);
			rootController.updateRightSide(welcome);
			break;

		case Persons:
			Parent persons = loadFxml(this, VIEW_PERSONS);
			rootController.updateRightSide(persons);
			break;

		case Invoices:
			Parent invoices = loadFxml(this, VIEW_INVOICES);
			rootController.updateRightSide(invoices);
			break;

		default:
			break;
		}
		if (rootController != null) {
			rootController.highlightLink();
		}
	}

	public SelectedView getView() {
		return view;
	}

	/**
	 * Load resource bundle from current locale.
	 */
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
	 * Get the resource bundle.
	 * 
	 * @return resource bundle
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * Check if current project was changed since the last save.
	 * 
	 * @return save
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return main stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public ObservableList<PersonModel> getPersonList() {
		return personList;
	}

	public ObservableList<String> getPersonNameList() {
		return personNameList;
	}

	public ObservableList<InvoiceModel> getInvoiceList() {
		return invoiceList;
	}

	/**
	 * Quit program (and show "Save Confirmation" dialog is necessary).
	 */
	public void quit() {
		primaryStage.fireEvent(new WindowEvent(primaryStage, WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Show about dialog.
	 */
	public void showAboutDialog() {
		Stage aboutStage = loadFxmlStage(this, VIEW_ABOUT, "ui.about.windowtitle");
		dialogs.showModalDialog(aboutStage);
	}

	public void followIllucitHyperlink() {
		getHostServices().showDocument("https://www.illucit.com");
	}

	/**
	 * Perform Menu Operation "New Project".
	 */
	public void menuNewProject() {
		dialogs.saveConfirm(() -> {
			newProject();
		});
	}

	/**
	 * Perform Menu Operation "Load Project".
	 */
	public void menuLoadProject() {
		dialogs.saveConfirm(() -> {
			loadProject();
		});
	}

	private void refreshObserableData() {
		this.personList.setAll(this.projectHolder.getProject().getPersons().stream().map(PersonModel::new)
				.collect(toList()));
		this.personNameList.setAll(this.projectHolder.getProject().getPersons().stream().map(ImmutablePerson::getName)
				.collect(toList()));
		this.invoiceList.setAll(this.projectHolder.getProject().getInvoices().stream().map(InvoiceModel::new)
				.collect(toList()));

		updateUndoRedo();
	}

	/**
	 * Create new project.
	 */
	private void newProject() {
		logger.info("Creating new project");
		initializeProject();
		refreshObserableData();
	}

	/**
	 * Create data for a new project
	 */
	private void initializeProject() {
		this.projectHolder = new ImmutableProjectHolder();
		this.xmlFile = null;
		this.changed = false;
	}

	/**
	 * Load project from file.
	 */
	private void loadProject() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(bundle.getString("ui.load.title"));
		File loadFile = fileChooser.showOpenDialog(primaryStage);

		if (loadFile == null) {
			return;
		}

		logger.info("Loading project from file {}", loadFile.getPath());

		try {
			XmlProject xmlProject = loadFromFile(loadFile);
			this.projectHolder = new ImmutableProjectHolder(xmlProject);
			this.xmlFile = loadFile;
			this.changed = false;

			refreshObserableData();

		} catch (JAXBException e) {
			logger.error("Error loading project", e);
			dialogs.showMessage("ui.message.errorload.title", "ui.message.errorload.text");
		}
	}

	/**
	 * Save project.
	 * 
	 * @return true, if was successful
	 */
	public boolean saveProject() {
		if (this.xmlFile == null) {
			return saveAsProject();
		}

		logger.info("Saving project to {}", this.xmlFile.getPath());

		try {
			saveToFile(this.xmlFile, projectHolder.getProject().getXmlProject());
			this.changed = false;
			return true;
		} catch (JAXBException e) {
			logger.error("Error saving project", e);
			dialogs.showMessage("ui.message.errorsave.title", "ui.message.errorsave.text");
		}
		return false;
	}

	/**
	 * Save project as new file.
	 * 
	 * @return true, if was successful
	 */
	public boolean saveAsProject() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(bundle.getString("ui.saveas.title"));
		File saveFile = fileChooser.showSaveDialog(primaryStage);

		if (saveFile == null) {
			return false;
		}

		logger.info("Saving project to file {}", saveFile.getPath());

		try {
			saveToFile(saveFile, projectHolder.getProject().getXmlProject());
			this.xmlFile = saveFile;
			this.changed = false;
			return true;
		} catch (JAXBException e) {
			logger.error("Error saving project", e);
			dialogs.showMessage("ui.message.errorsave.title", "ui.message.errorsave.text");
		}
		return false;
	}

	public void performOperation(Operation operation) {
		modifyProjectHolder(operation::operate);
	}

	public void undo() {
		modifyProjectHolder(ImmutableProjectHolder::undo);
	}

	public void redo() {
		modifyProjectHolder(ImmutableProjectHolder::redo);
	}

	private void modifyProjectHolder(Function<ImmutableProjectHolder, ImmutableProjectHolder> transformation) {
		this.projectHolder = transformation.apply(projectHolder);
		refreshObserableData();
		this.changed = true;
	}

	private void updateUndoRedo() {
		if (rootController != null) {
			rootController.setUndoEnabled(projectHolder.hasUndoStep());
			rootController.setRedoEnabled(projectHolder.hasRedoStep());
		}
	}

	/*
	 * Operations
	 */

	public void addPerson(String name) {
		performOperation(new AddPersonOp(name));
	}

	public void deletePerson(String name) {
		performOperation(new DelPersonOp(name));
	}

	public void addInvoice(String title, String paidBy) {
		performOperation(new AddInvoiceOp(title, paidBy));
	}

	public void deleteInvoice(String title) {
		performOperation(new DelInvoiceOp(title));
	}

}
