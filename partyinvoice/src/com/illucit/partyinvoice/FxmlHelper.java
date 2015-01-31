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

	public static <T> T loadFxml(PartyInvoiceApp app, String view) {
		return loadFxml(app, view, Object.class, null);
	}

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
			AbstractController cntrlr = (AbstractController) controller;
			cntrlr.setApp(app);
			cntrlr.setStage(app.getPrimaryStage());
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
	 * @param <C>
	 *            Controller type
	 * @return the created stage or null, of the loading the FXML failed
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
	 *            handle to be called when the FXML and the controller are
	 *            loaded
	 * @return the created stage or null, of the loading the FXML failed
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
			AbstractController cntrlr = (AbstractController) controller;
			cntrlr.setApp(app);
			cntrlr.setStage(stage);
		}

		if (handler != null) {
			handler.handleStage(stage, controller);
		}

		return stage;
	}

	@FunctionalInterface
	public static interface StageHandler<C> {

		public void handleStage(Stage stage, C controller);

	}

	@FunctionalInterface
	public static interface InnerDocumentHandler<C> {

		public void handleDocument(C controller);

	}

}
