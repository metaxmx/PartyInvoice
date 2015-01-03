package com.illucit.partyinvoice.ui.view;

public class SaveConfirmController {

	public enum ConfirmResult {
		SAVE, DISCARD, CANCEL
	}

	private ConfirmCallback callback = null;

	public void setCallback(ConfirmCallback callback) {
		this.callback = callback;
	}

	public void cancel() {
		callback.call(ConfirmResult.CANCEL);
	}

	public void save() {
		callback.call(ConfirmResult.SAVE);
	}

	public void discard() {
		callback.call(ConfirmResult.DISCARD);
	}

	@FunctionalInterface
	public static interface ConfirmCallback {

		public void call(ConfirmResult result);

	}
}
