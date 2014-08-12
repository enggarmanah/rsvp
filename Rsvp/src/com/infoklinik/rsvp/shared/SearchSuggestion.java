package com.infoklinik.rsvp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class SearchSuggestion implements IsSerializable, Suggestion {
	
	private String displayShort;
	private String displayLong;
	private String value;

	public String getDisplayString() {
		return displayLong;
	}
	
	public String getValue() {
		return value;
	}

	public String getReplacementString() {
		return displayShort;
	}

	public SearchSuggestion() {
	}

	public SearchSuggestion(String value) {
		this.value = value;
		this.displayShort = value;
		this.displayLong = value;
	}
	
	public SearchSuggestion(String value, String displayShort) {
		this.value = value;
		this.displayShort = displayShort;
		this.displayLong = displayShort;
	}
	
	public SearchSuggestion(String value, String displayShort, String displayLong) {
		this.value = value;
		this.displayShort = displayShort;
		this.displayLong = displayLong;
	}

}