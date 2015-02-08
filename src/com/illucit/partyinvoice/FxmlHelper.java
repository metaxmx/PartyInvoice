package com.illucit.partyinvoice;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for loading FXML documents.
 * 
 * @author Christian Simon
 *
 */
public class FxmlHelper {

	private static final Logger logger = LoggerFactory.getLogger(FxmlHelper.class);

	private static final String VIEW_PREFIX = "view/";

	/**
	 * Load view from FXML file. No handler for Stage and controller is given.
	 * If the controller is an {@link AbstractController}, the app is
	 * automatically assigned.
	 * 
	 * @param app
	 *            the app
	 * @param view
	 *            view location (relative to the view prefix
	 *            {@link #VIEW_PREFIX} in the same package of this class)
	 * @param <T>
	 *            parent element class
	 * @return parent of the FXML document
	 */
	public static <T> T loadFxml(PartyInvoiceApp app, String view) {
		return loadFxml(app, view, Object.class, null);
	}

	/**
	 * Load view from FXML file with a handle for the controller. If the
	 * controller is an {@link AbstractController}, the app is automatically
	 * assigned.
	 * 
	 * @param app
	 *            the app
	 * @param view
	 *            view location (relative to the view prefix
	 *            {@link #VIEW_PREFIX} in the same package of this class)
	 * @param controllerClass
	 *            class the controller should have
	 * @param handler
	 *            handler to be called when the FXML and the controller are
	 *            loaded
	 * @param <C>
	 *            controller class
	 * @param <T>
	 *            parent element class
	 * @return parent of the FXML document
	 */
	public static <C, T> T loadFxml(PartyInvoiceApp app, String view, Class<C> controllerClass,
			InnerDocumentHandler<C> handler) {

		logger.info("Loading FXML file " + VIEW_PREFIX + view);

		Localization l10n = Localization.getInstance();

		FXMLLoader loader = new FXMLLoader();
		loader.setResources(l10n.getBundle());
		loader.setLocation(FxmlHelper.class.getResource(VIEW_PREFIX + view));

		if (loader.getLocation() == null) {
			logger.error("View not found: {}/{}", FxmlHelper.class.getPackage().getName().replace('.', '/'),
					VIEW_PREFIX + view);
			return null;
		}

		T viewParent;

		try {
			viewParent = loader.load();
		} catch (IOException e) {
			logger.error("Error loading view", e);
			return null;
		}

		// Give the controller access to the main app.
		C controller = loader.getController();
		if (controller instanceof AbstractController) {
			AbstractController abstractController = (AbstractController) controller;
			abstractController.setApp(app);
			abstractController.setStage(app.getPrimaryStage());
		}

		if (handler != null) {
			handler.handleDocument(controller);
		}

		return viewParent;
	}

	/**
	 * Load Stage from FXML file. No handler for Stage and controller is given.
	 * If the controller is an {@link AbstractController}, the app is
	 * automatically assigned.
	 * 
	 * @param app
	 *            the app
	 * @param view
	 *            view location (relative to the view prefix
	 *            {@link #VIEW_PREFIX} in the same package of this class)
	 * @param titleKey
	 *            the message key to be used for the stage
	 * @return the created stage or null, if the loading of the FXML failed
	 */
	public static Stage loadFxmlStage(PartyInvoiceApp app, String view, String titleKey) {
		return loadFxmlStage(app, view, titleKey, Object.class, null);
	}

	/**
	 * Load Stage from FXML file with a handler for the controller. If the
	 * controller is an {@link AbstractController}, the app is automatically
	 * assigned.
	 * 
	 * @param app
	 *            the app
	 * @param view
	 *            view location (relative to the view prefix
	 *            {@link #VIEW_PREFIX} in the same package of this class)
	 * @param titleKey
	 *            the message key to be used for the stage
	 * @param controllerClass
	 *            class the controller should have
	 * @param handler
	 *            handler to be called when the FXML and the controller are
	 *            loaded
	 * @param <C>
	 *            controller class
	 * @return the created stage or null, if the loading of the FXML failed
	 */
	public static <C> Stage loadFxmlStage(PartyInvoiceApp app, String view, String titleKey, Class<C> controllerClass,
			StageHandler<C> handler) {

		logger.info("Loading FXML file " + VIEW_PREFIX + view);

		Localization l10n = Localization.getInstance();

		FXMLLoader loader = new FXMLLoader();
		loader.setResources(l10n.getBundle());
		loader.setLocation(FxmlHelper.class.getResource(VIEW_PREFIX + view));

		if (loader.getLocation() == null) {
			logger.error("View not found: {}/{}", FxmlHelper.class.getPackage().getName().replace('.', '/'),
					VIEW_PREFIX + view);
			return null;
		}

		Parent viewParent;

		try {
			viewParent = loader.load();
		} catch (IOException e) {
			logger.error("Error loading view", e);
			return null;
		}

		Stage stage = new Stage();
		stage.setTitle(l10n.getString(titleKey));
		stage.setScene(new Scene(viewParent));

		// Give the controller access to the main app.
		C controller = loader.getController();
		if (controller instanceof AbstractController) {
			AbstractController abstractController = (AbstractController) controller;
			abstractController.setApp(app);
			abstractController.setStage(stage);
		}

		if (handler != null) {
			handler.handleStage(stage, controller);
		}

		return stage;
	}

	/**
	 * Functional interface for to handle a loaded stage.
	 * 
	 * @author Christian Simon
	 *
	 * @param <C>
	 *            controller type
	 */
	@FunctionalInterface
	public static interface StageHandler<C> {

		/**
		 * Handle the stage and controller has been loaded.
		 * 
		 * @param stage
		 *            created stage
		 * @param controller
		 *            loaded controller
		 */
		public void handleStage(Stage stage, C controller);

	}

	/**
	 * Functional interface for to handle a loaded inner document.
	 * 
	 * @author Christian Simon
	 *
	 * @param <C>
	 *            controller type
	 */
	@FunctionalInterface
	public static interface InnerDocumentHandler<C> {

		/**
		 * Handle the inner document and controller has been loaded.
		 * 
		 * @param controller
		 *            loaded controller
		 */
		public void handleDocument(C controller);

	}

}
