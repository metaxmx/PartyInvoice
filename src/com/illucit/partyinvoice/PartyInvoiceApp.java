package com.illucit.partyinvoice;

import static com.illucit.partyinvoice.FxmlHelper.loadFxml;
import static com.illucit.partyinvoice.FxmlHelper.loadFxmlStage;
import static com.illucit.partyinvoice.XmlIO.loadFromFile;
import static com.illucit.partyinvoice.XmlIO.saveToFile;
import static com.illucit.partyinvoice.util.LogSetup.setupLogging;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
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

import com.illucit.partyinvoice.data.BaseData;
import com.illucit.partyinvoice.data.Operation;
import com.illucit.partyinvoice.immutabledata.ImmutableGroup;
import com.illucit.partyinvoice.immutabledata.ImmutableItem;
import com.illucit.partyinvoice.immutabledata.ImmutablePerson;
import com.illucit.partyinvoice.immutabledata.ImmutableProjectHolder;
import com.illucit.partyinvoice.immutabledata.operation.AddInvoiceOp;
import com.illucit.partyinvoice.immutabledata.operation.AddItemOp;
import com.illucit.partyinvoice.immutabledata.operation.AddPersonOp;
import com.illucit.partyinvoice.immutabledata.operation.ChangeInvoiceOp;
import com.illucit.partyinvoice.immutabledata.operation.ChangeItemOp;
import com.illucit.partyinvoice.immutabledata.operation.ChangePersonOp;
import com.illucit.partyinvoice.immutabledata.operation.DelInvoiceOp;
import com.illucit.partyinvoice.immutabledata.operation.DelItemOp;
import com.illucit.partyinvoice.immutabledata.operation.DelPersonOp;
import com.illucit.partyinvoice.model.BaseModel;
import com.illucit.partyinvoice.model.InvoiceModel;
import com.illucit.partyinvoice.model.ItemModel;
import com.illucit.partyinvoice.model.PersonListModel;
import com.illucit.partyinvoice.model.PersonModel;
import com.illucit.partyinvoice.model.ResultModel;
import com.illucit.partyinvoice.model.ToPayModel;
import com.illucit.partyinvoice.model.ToPayModel.ToPayType;
import com.illucit.partyinvoice.view.RootController;
import com.illucit.partyinvoice.xmldata.XmlProject;

/**
 * Main Application class for Party Invoice.
 * 
 * @author Christian Simon
 *
 */
public class PartyInvoiceApp extends Application {

	/**
	 * Enum for the selected right-side view in the main window.
	 * 
	 * @author Christian Simon
	 *
	 */
	public enum SelectedView {
		/** Welcome page */
		Welcome(VIEW_WELCOME),

		/** Person list */
		Persons(VIEW_PERSONS),

		/** Invoices / Items */
		Invoices(VIEW_INVOICES),

		/** Result list */
		Result(VIEW_RESULT);

		private final String view;

		private SelectedView(String view) {
			this.view = view;
		}

		/**
		 * Get the FXML file of the view (relative to the view root
		 * com/illucit/partyinvoice/view).
		 * 
		 * @return path to FXML file as String
		 */
		public String getView() {
			return view;
		}
	}

	/** Root view for main window (including menu). */
	public static final String VIEW_ROOT = "RootLayout.fxml";
	/** View with the contents of the main window. */
	public static final String VIEW_MAIN = "MainLayout.fxml";
	/** View for the message box. */
	public static final String VIEW_MESSAGE = "MessageView.fxml";
	/** View for the "Save?" confirmation dialog. */
	public static final String VIEW_SAVECONFIRM = "SaveConfirmView.fxml";
	/** Vioew for the about dialog. */
	public static final String VIEW_ABOUT = "AboutView.fxml";

	/** Right-side view "Welcome". */
	public static final String VIEW_WELCOME = "WelcomeView.fxml";
	/** Right-side view "Persons". */
	public static final String VIEW_PERSONS = "PersonView.fxml";
	/** Right-side view "Invoices". */
	public static final String VIEW_INVOICES = "InvoicesView.fxml";
	/** Right-side view "Result". */
	public static final String VIEW_RESULT = "ResultView.fxml";

	private final Map<String, Parent> loadedViews = new ConcurrentHashMap<>();

	private File userSettingsDir;

	private Logger logger = LoggerFactory.getLogger(PartyInvoiceApp.class.getName());

	private Localization l10n = Localization.getInstance();

	private Stage primaryStage;

	private BorderPane rootLayout;

	private RootController rootController;

	private Dialogs dialogs;

	private SelectedView view = SelectedView.Welcome;

	private ImmutableProjectHolder projectHolder;

	private File xmlFile = null;
	private boolean changed = false;

	private ObservableList<PersonModel> personList;

	private ObservableList<PersonListModel> personNameList;

	private ObservableList<PersonListModel> personNameListNullable;

	private ObservableList<InvoiceModel> invoiceList;

	private ReadOnlyObjectProperty<InvoiceModel> selectedInvoiceProperty = null;

	private ObservableList<ItemModel> itemList;

	private ObservableList<ToPayModel> toPayList;

	private ObservableList<ResultModel> resultList;

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

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(l10n.getString("ui.title"));
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
		personNameListNullable = FXCollections.observableArrayList();
		invoiceList = FXCollections.observableArrayList();
		itemList = FXCollections.observableArrayList();
		toPayList = FXCollections.observableArrayList();
		resultList = FXCollections.observableArrayList();

		// Add default "All" entry
		toPayList.add(new ToPayModel());

		initializeProject();

		initUi();

		refreshObserableData(true);

		primaryStage.show();
	}

	/**
	 * Change locale and reload UI.
	 * 
	 * @param locale
	 *            target locale
	 */
	public void changeLocale(Locale locale) {
		l10n.changeLocale(locale);
		loadedViews.clear();
		initUi();
	}

	/**
	 * Initialize UI.
	 */
	private void initUi() {
		rootLayout = loadFxml(this, VIEW_ROOT, RootController.class, (controller) -> {
			rootController = controller;
		});

		selectView(SelectedView.Welcome);

		// Show the scene containing the root layout.
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
	}

	/**
	 * Select a view to be active.
	 * 
	 * @param selectedView
	 *            right side view that should be active
	 */
	public void selectView(SelectedView selectedView) {
		this.view = selectedView;
		String viewFile = selectedView.getView();

		Parent parent = loadedViews.computeIfAbsent(viewFile, v -> loadFxml(this, v));
		rootController.updateRightSide(parent);
		rootController.highlightLink();
	}

	/**
	 * Get the active right side view.
	 * 
	 * @return active right side view
	 */
	public SelectedView getView() {
		return view;
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

	/**
	 * Get the observable list containing the persons of the current project.
	 * 
	 * @return list of person models
	 */
	public ObservableList<PersonModel> getPersonList() {
		return personList;
	}

	/**
	 * Get the observable list containing the person names of the current
	 * project.
	 * 
	 * @return list of person names
	 */
	public ObservableList<PersonListModel> getPersonNameList() {
		return personNameList;
	}

	/**
	 * Get the observable list containing the person names of the current
	 * project, include an empty entry which can be interpretet as null.
	 * 
	 * @return list of person names
	 */
	public ObservableList<PersonListModel> getPersonNameListNullable() {
		return personNameListNullable;
	}

	/**
	 * Get the observable list containing the invoice models of the current
	 * project.
	 * 
	 * @return list of invoice models
	 */
	public ObservableList<InvoiceModel> getInvoiceList() {
		return invoiceList;
	}

	/**
	 * Set the object property binding for the currently selected invoice in the
	 * {@link SelectedView#Invoices} view. A change handler for the selected
	 * invoice is added to refresh the data for the item list belonging to
	 * current invoice.
	 * 
	 * @param selectedInvoiceProperty
	 *            property for the selected invoice
	 */
	public void setSelectedInvoiceProperty(ReadOnlyObjectProperty<InvoiceModel> selectedInvoiceProperty) {
		this.selectedInvoiceProperty = selectedInvoiceProperty;
		this.selectedInvoiceProperty.addListener((observable, oldVal, newVal) -> refreshItemData(newVal, false));
	}

	/**
	 * Get the observable list containing the item models of the currently
	 * selected invoice.
	 * 
	 * @return list of item models
	 */
	public ObservableList<ItemModel> getItemList() {
		return itemList;
	}

	/**
	 * Get the observable list containing the "to pay" models of the current
	 * project:
	 * <ul>
	 * <li>Everybody</li>
	 * <li>One model for each group</li>
	 * <li>One model for each person</li>
	 * </ul>
	 * 
	 * @return list of "to pay" models
	 */
	public ObservableList<ToPayModel> getToPayList() {
		return toPayList;
	}

	/**
	 * Get the observable list containing the result models of the current
	 * project.
	 * 
	 * @return list of result models
	 */
	public ObservableList<ResultModel> getResultList() {
		return resultList;
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

	/**
	 * Open the web browser and display the illucit.com website.
	 */
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

	/**
	 * Refresh all observable lists to reflact changes in the data.
	 * 
	 * @param clear
	 *            flag if existing models should be updated if possible (clear =
	 *            false) or all models should be recreated (clear = true)
	 */
	private void refreshObserableData(boolean clear) {
		updateList(personList, projectHolder.getProject().getPersons(), PersonModel::new, clear);
		updateList(personNameList, projectHolder.getProject().getPersons(), PersonListModel::new, clear);
		updateList(personNameListNullable, getPersonListWithNull(), PersonListModel::new, clear);
		updateList(invoiceList, projectHolder.getProject().getInvoices(), InvoiceModel::new, clear);
		updateToPayModelList(clear);
		updateList(resultList, projectHolder.getProject().getPersons(), ResultModel::new, clear);

		if (this.selectedInvoiceProperty != null) {
			refreshItemData(this.selectedInvoiceProperty.get(), clear);
		}

		updateUndoRedo();
	}

	/**
	 * Construct a list which will contain all {@link ImmutablePerson} entries
	 * defind in the project with an additional entry with id = 0 which can be
	 * interpretet as "null".
	 * 
	 * @return list with {@link ImmutablePerson}
	 */
	private List<ImmutablePerson> getPersonListWithNull() {
		// Add pseudo person wiothout name and with id 0 to emulate "null" entry
		ImmutablePerson nullPerson = new ImmutablePerson(0, "", 0, 0);
		LinkedList<ImmutablePerson> result = new LinkedList<>();
		result.add(nullPerson);
		result.addAll(projectHolder.getProject().getPersons());
		return result;
	}

	/**
	 * Refresh the list of items which belong to the currently selected invoice.
	 * 
	 * @param model
	 *            current invoice model
	 * @param clear
	 *            flag if existing models should be updated if possible (clear =
	 *            false) or all models should be recreated (clear = true)
	 */
	private void refreshItemData(InvoiceModel model, boolean clear) {
		if (model == null) {
			itemList.clear();
		} else {
			List<ImmutableItem> updatedItems = this.projectHolder.getProject().getInvoices().stream()
					.filter(iv -> iv.getId() == model.getId()).flatMap(iv -> iv.getItems().stream()).collect(toList());
			updateList(itemList, updatedItems, ItemModel::new, clear);

		}
	}

	/**
	 * Create new project.
	 */
	private void newProject() {
		logger.info("Creating new project");
		initializeProject();
		refreshObserableData(true);
	}

	/**
	 * Create data for a new project.
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
		fileChooser.setTitle(l10n.getString("ui.load.title"));
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

			refreshObserableData(true);

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
		fileChooser.setTitle(l10n.getString("ui.saveas.title"));
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

	/**
	 * Perform any abstract operation to the current project state and then
	 * reload the observable data.
	 * 
	 * @param operation
	 *            operation to perform
	 */
	public void performOperation(Operation operation) {
		modifyProjectHolder(operation::operate);
	}

	/**
	 * Reset project to the latest undo step.
	 */
	public void undo() {
		modifyProjectHolder(ImmutableProjectHolder::undo);
	}

	/**
	 * Forward project to the next redo step.
	 */
	public void redo() {
		modifyProjectHolder(ImmutableProjectHolder::redo);
	}

	/**
	 * Modify the project holder by any project holder transformation and then
	 * reload the observable data.
	 * 
	 * @param transformation
	 *            transformation to perform
	 */
	private void modifyProjectHolder(Function<ImmutableProjectHolder, ImmutableProjectHolder> transformation) {
		this.projectHolder = transformation.apply(projectHolder);
		refreshObserableData(false);
		this.changed = true;
	}

	/**
	 * Set menu entries for "redo"/"undo" to enabled/disabled for the current
	 * project holder state.
	 */
	private void updateUndoRedo() {
		if (rootController != null) {
			rootController.setUndoEnabled(projectHolder.hasUndoStep());
			rootController.setRedoEnabled(projectHolder.hasRedoStep());
		}
	}

	/**
	 * Abstract method to refresh an {@link ObservableList} and maintian
	 * existing models.
	 * 
	 * @param observableList
	 *            list that should be changed
	 * @param updatedData
	 *            base data for the updated state
	 * @param constructor
	 *            constructor for the model type from the data of a base data
	 *            type
	 * @param clear
	 *            flag if existing models should be updated if possible (clear =
	 *            false) or all models should be recreated (clear = true)
	 * @param <D>
	 *            base data type
	 * @param <M>
	 *            base model type (compatible to &lt;D&gt;)
	 */
	private static <D extends BaseData, M extends BaseModel<D>> void updateList(ObservableList<M> observableList,
			List<D> updatedData, Function<D, M> constructor, boolean clear) {

		if (clear) {

			// Clear list and add new models

			observableList.setAll(updatedData.stream().map(constructor).collect(toList()));

		} else {

			// Try to retain existing models

			Set<Integer> remainingIds = updatedData.stream().map(data -> data.getId()).collect(toSet());
			Map<Integer, M> modelsById = observableList.stream().collect(toMap(model -> model.getId(), model -> model));

			// Remove deleted models
			modelsById.keySet().forEach(id -> {
				if (!remainingIds.contains(id)) {
					observableList.remove(modelsById.get(id));
				}
			});

			// Update and Add models
			for (D data : updatedData) {
				if (modelsById.containsKey(data.getId())) {
					// Update
					modelsById.get(data.getId()).update(data);
				} else {
					// Add
					observableList.add(constructor.apply(data));
				}
			}

		}

	}

	/**
	 * Update "to pay" list.
	 * 
	 * @param clear
	 *            flag if existing models should be updated if possible (clear =
	 *            false) or all models should be recreated (clear = true)
	 */
	private void updateToPayModelList(boolean clear) {

		if (clear) {

			// Clear list (except for default "all" entry) and add new models

			ToPayModel defaultModel = toPayList.stream().filter(m -> m.getType() == ToPayType.All).findFirst().get();
			toPayList.retainAll(defaultModel);

			for (ImmutablePerson person : projectHolder.getProject().getPersons()) {
				toPayList.add(new ToPayModel(person));
			}
			for (ImmutableGroup group : projectHolder.getProject().getGroups()) {
				toPayList.add(new ToPayModel(group));
			}

		} else {

			// Try to retain existing person models

			Set<Integer> remainingPersonIds = projectHolder.getProject().getPersons().stream()
					.map(data -> data.getId()).collect(toSet());
			Map<Integer, ToPayModel> modelsByPersonId = toPayList.stream().filter(m -> m.getType() == ToPayType.Person)
					.collect(toMap(model -> model.getPerson().getId(), model -> model));

			// Remove deleted person models
			modelsByPersonId.keySet().forEach(id -> {
				if (!remainingPersonIds.contains(id)) {
					toPayList.remove(modelsByPersonId.get(id));
				}
			});

			// Update and Add person models
			for (ImmutablePerson person : projectHolder.getProject().getPersons()) {
				if (modelsByPersonId.containsKey(person.getId())) {
					// Update
					modelsByPersonId.get(person.getId()).update(person);
				} else {
					// Add
					toPayList.add(new ToPayModel(person));
				}
			}

			// Try to retain existing group models

			Set<Integer> remainingGroupIds = projectHolder.getProject().getGroups().stream().map(data -> data.getId())
					.collect(toSet());
			Map<Integer, ToPayModel> modelsByGroupId = toPayList.stream().filter(m -> m.getType() == ToPayType.Group)
					.collect(toMap(model -> model.getGroup().getId(), model -> model));

			// Remove deleted person models
			modelsByGroupId.keySet().forEach(id -> {
				if (!remainingGroupIds.contains(id)) {
					toPayList.remove(modelsByGroupId.get(id));
				}
			});

			// Update and Add person models
			for (ImmutableGroup group : projectHolder.getProject().getGroups()) {
				if (modelsByGroupId.containsKey(group.getId())) {
					// Update
					modelsByGroupId.get(group.getId()).update(group);
				} else {
					// Add
					toPayList.add(new ToPayModel(group));
				}
			}

		}

	}

	/*
	 * Operations
	 */

	/**
	 * Perform operation: add person.
	 * 
	 * @param name
	 *            new name
	 */
	public void addPerson(String name) {
		performOperation(new AddPersonOp(name));
	}

	/**
	 * Perform operation: change person.
	 * 
	 * @param id
	 *            id of existing person
	 * @param newName
	 *            new name of person
	 */
	public void changePerson(int id, String newName) {
		performOperation(new ChangePersonOp(id, newName));
	}

	/**
	 * Perform operation: delete person.
	 * 
	 * @param id
	 *            id of existing person
	 */
	public void deletePerson(int id) {
		performOperation(new DelPersonOp(id));
	}

	/**
	 * Perform operation: add invoice.
	 * 
	 * @param title
	 *            title of new invoice
	 * @param paidBy
	 *            "paid by" person of new invoice
	 */
	public void addInvoice(String title, int paidBy) {
		performOperation(new AddInvoiceOp(title, paidBy));
	}

	/**
	 * Perform operation: change invoice.
	 * 
	 * @param id
	 *            id of existing invoice
	 * @param newTitle
	 *            new title of invoice
	 * @param paidBy
	 *            new "paid by" person of invoice
	 * */
	public void changeInvoice(int id, String newTitle, int paidBy) {
		performOperation(new ChangeInvoiceOp(id, newTitle, paidBy));
	}

	/**
	 * Perform operation: delete invoice.
	 * 
	 * @param id
	 *            id of existing invoice
	 */
	public void deleteInvoice(int id) {
		performOperation(new DelInvoiceOp(id));
	}

	/**
	 * Perform operation: add item to selected invoice.
	 * 
	 * @param title
	 *            title of new item
	 * @param price
	 *            price of new item
	 * @param quantity
	 *            quantity of new item
	 * @param paidBy
	 *            "paid by" person of new item
	 * @param personToPay
	 *            "person to pay" person of new item
	 * @param groupToPay
	 *            "group to pay" group of new item
	 */
	public void addItem(String title, long price, int quantity, Integer paidBy, Integer personToPay, Integer groupToPay) {
		InvoiceModel selectedInvoice = this.selectedInvoiceProperty.get();
		if (selectedInvoice == null) {
			return;
		}
		performOperation(new AddItemOp(selectedInvoice.getId(), title, price, quantity, paidBy, personToPay, groupToPay));
	}

	/**
	 * Perform operation: change item.
	 * 
	 * @param id
	 *            id of existing item
	 * @param title
	 *            new title of item
	 * @param price
	 *            new price of item
	 * @param quantity
	 *            new quantity of item
	 * @param paidBy
	 *            new "paid by" person of item
	 * @param personToPay
	 *            new "person to pay" person of item
	 * @param groupToPay
	 *            new "group to pay" group of item
	 */
	public void changeItem(int id, String title, long price, int quantity, Integer paidBy, Integer personToPay,
			Integer groupToPay) {
		performOperation(new ChangeItemOp(id, title, price, quantity, paidBy, personToPay, groupToPay));
	}

	/**
	 * Perform operation: delete item.
	 * 
	 * @param id
	 *            id of existing item
	 */
	public void deleteItem(int id) {
		performOperation(new DelItemOp(id));
	}

}
