package com.illucit.partyinvoice.data;

import java.util.LinkedList;
import java.util.List;

public class Invoice {

	private String title;

	private List<Article> articles = new LinkedList<>();

	private String paidBy;

	public String getTitle() {
		return title;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

}
