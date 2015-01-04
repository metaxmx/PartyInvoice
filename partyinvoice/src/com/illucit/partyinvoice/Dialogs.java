package com.illucit.partyinvoice;

import static com.illucit.partyinvoice.FxmlHelper.loadFxmlStage;
import static com.illucit.partyinvoice.PartyInvoiceApp.VIEW_MESSAGE;
import static com.illucit.partyinvoice.PartyInvoiceApp.VIEW_SAVECONFIRM;

import java.util.ResourceBundle;

import javafx.stage.Modality;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.illucit.partyinvoice.view.MessageController;
import com.illucit.partyinvoice.view.SaveConfirmController;
import com.illucit.partyinvoice.view.SaveConfirmController.ConfirmResult;

/**
 * Common dialogs helper.
 * 
 * @author Christian Simon
 *
 */
public class Dialogs {

	private static final Logger logger = LoggerFactory.getLogger(Dialogs.class);

	private final PartyInvoiceApp app;

	public Dialogs(PartyInvoiceApp app) {
		this.app = app;
	}

	public void showMessage(String messageTitleKey, String messageKey) {
		Stage messageStage = loadFxmlStage(app, VIEW_MESSAGE, messageTitleKey, MessageController.class, (stage,
				controller, bundle) -> {
			controller.setMessage(bundle.getString(messageKey));
			controller.setStage(stage);
		});
		if (messageStage == null) {
			// Error loading FXML
			ResourceBundle bundle = app.getBundle();
			logger.warn("Error showing the following message: {} - {}", bundle.getString(messageTitleKey),
					bundle.getString(messageKey));
			return;
		}

		showModalDialog(messageStage);
	}

	public void saveConfirm(Runnable callback) {
		if (!app.isChanged()) {
			// No confirmation needed
			callback.run();
			return;
		}

		Stage cStage = loadFxmlStage(app, VIEW_SAVECONFIRM, "ui.saveconfirm.title", SaveConfirmController.class, (
				stage, controller, bundle) -> {
			controller.setCallback(result -> {
				stage.close();
				if (result == ConfirmResult.DISCARD) {
					// Discard values - continue with action
					callback.run();
				} else if (result == ConfirmResult.SAVE) {
					// Try to save and continue is save was successful
					boolean saved = app.saveProject();
					if (saved) {
						callback.run();
					}
				}
			});
		});
		if (cStage == null) {
			// Error loading FXML
			logger.warn("Error showing a confirm message whether to save the project - assuming 'save' was pressed");
			boolean saved = app.saveProject();
			if (saved) {
				callback.run();
			}
			return;
		}

		showModalDialog(cStage);
	}

	/**
	 * Show a (dialog) stage modal to the main window and wait for it to close.
	 * The stage is not resizable.
	 * 
	 * @param stage
	 *            stage to show
	 */
	private void showModalDialog(Stage stage) {
		stage.setResizable(false);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(app.getPrimaryStage());
		stage.showAndWait();
	}
}
