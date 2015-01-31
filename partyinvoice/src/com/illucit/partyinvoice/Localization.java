package com.illucit.partyinvoice;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Singleton i18n bean.
 * 
 * @author Christian Simon
 *
 */
public class Localization {

	/** Name of the resource bundle. */
	private static final String BUNDLE_NAME = "partyinvoice";

	private static Localization instance = new Localization();

	public static Localization getInstance() {
		return instance;
	}

	private Localization() {
		loadResourceBundle();
	}

	private Locale locale = Locale.getDefault();
	private ResourceBundle bundle;

	public String getString(String key) {
		return bundle.getString(key);
	}

	public void changeLocale(Locale locale) {
		this.locale = locale;
		loadResourceBundle();
	}
	
	public ResourceBundle getBundle() {
		return bundle;
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

}
