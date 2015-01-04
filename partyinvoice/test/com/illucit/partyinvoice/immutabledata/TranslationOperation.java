package com.illucit.partyinvoice.immutabledata;

import java.io.Serializable;

public class TranslationOperation implements Serializable {

	private static final long serialVersionUID = -7689335164797631962L;

	private final int x;

	private final int y;

	public TranslationOperation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
