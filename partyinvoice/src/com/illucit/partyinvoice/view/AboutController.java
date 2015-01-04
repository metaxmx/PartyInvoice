package com.illucit.partyinvoice.view;

import com.illucit.partyinvoice.AbstractController;

/**
 * Controller for the "About" dialog.
 * 
 * @author Christian Simon
 *
 */
public class AboutController extends AbstractController {

	public void close() {
		getApp().closeAboutDialog();
	}

	public void followHyperlink() {
		getApp().followIllucitHyperlink();
	}

}
