package com.illucit.partyinvoice;

import java.io.IOException;
import java.util.ResourceBundle;

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
	 * @return the created stage or null, of the loading the FXML failed
	 */
	public static <T> Stage loadFxmlStage(PartyInvoiceApp app, String view, String titleKey) {
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
	public static <T> Stage loadFxmlStage(PartyInvoiceApp app, String view, String titleKey, Class<T> controllerClass,
			StageHandler<T> handler) {
		ResourceBundle bundle = app.getBundle();

		FXMLLoader loader = new FXMLLoader();
		loader.setResources(bundle);
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

		// Give the controller access to the main app.
		T controller = loader.getController();
		if (controller instanceof AbstractController) {
			((AbstractController) controller).setApp(app);
		}

		Stage stage = new Stage();
		stage.setTitle(bundle.getString(titleKey));
		stage.setScene(new Scene(viewParent));

		if (handler != null) {
			handler.handleStage(stage, controller, bundle);
		}

		return stage;
	}

	@FunctionalInterface
	public static interface StageHandler<T> {

		public void handleStage(Stage stage, T controller, ResourceBundle bundle);

	}

}
