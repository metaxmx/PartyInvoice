package com.illucit.partyinvoice.view;

import javafx.fxml.FXML;

/**
 * Controller for the Save confirmation.
 * 
 * @author Christian Simon
 *
 */
public class SaveConfirmController {

	/**
	 * Possible outcomes of the dialog.
	 * 
	 * @author Christian Simon
	 *
	 */
	public enum ConfirmResult {
		/** Project should be saved. */
		SAVE,
		/** Changes should be discarded. */
		DISCARD,
		/** Operation should be cancelled. */
		CANCEL
	}

	private ConfirmCallback callback = null;

	/**
	 * Set callback function to be called after the disloag closes.
	 * 
	 * @param callback
	 *            callback function.
	 */
	public void setCallback(ConfirmCallback callback) {
		this.callback = callback;
	}

	/**
	 * Select the {@link ConfirmResult#CANCEL} option and close dialog.
	 */
	@FXML
	public void cancel() {
		callback.call(ConfirmResult.CANCEL);
	}

	/**
	 * Select the {@link ConfirmResult#SAVE} option and close dialog.
	 */
	@FXML
	public void save() {
		callback.call(ConfirmResult.SAVE);
	}

	/**
	 * Select the {@link ConfirmResult#DISCARD} option and close dialog.
	 */
	@FXML
	public void discard() {
		callback.call(ConfirmResult.DISCARD);
	}

	/**
	 * (Functional) Interface for a callback, which should be performed after
	 * the user selected an option from the dialog.
	 * 
	 * @author Christian Simon
	 *
	 */
	@FunctionalInterface
	public static interface ConfirmCallback {

		/**
		 * Call callback.
		 * 
		 * @param result
		 *            result from dialog
		 */
		public void call(ConfirmResult result);

	}
}
