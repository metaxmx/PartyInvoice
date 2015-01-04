package com.illucit.partyinvoice.xmldata;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.illucit.partyinvoice.CurrencyUtil;

/**
 * Type adapter to store currency values as String.
 * 
 * @author Christian Simon
 *
 */
public class CurrencyTypeAdapter extends XmlAdapter<String, Long> {

	@Override
	public Long unmarshal(String v) throws Exception {
		if (v == null) {
			return null;
		}
		return CurrencyUtil.stringToCurrency(v);
	}

	@Override
	public String marshal(Long v) throws Exception {
		if (v == null) {
			return null;
		}
		return CurrencyUtil.currencyToString(v);
	}

}
