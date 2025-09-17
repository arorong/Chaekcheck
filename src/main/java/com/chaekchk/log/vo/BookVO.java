package com.chaekchk.log.vo;

import java.time.LocalDate;

public class BookVO {

	private int b_id;
	private String title;
	private String author;
	private LocalDate pubdate;
	private String description;
	private String isbn;
	private String cover;
	private String publisher;
	
	
	public int getB_id() {
		return b_id;
	}
	public void setB_id(int b_id) {
		this.b_id = b_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public LocalDate getPubdate() {
		return pubdate;
	}
	public void setPubdate(LocalDate pubdate) {
		this.pubdate = pubdate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	@Override
	public String toString() {
		return "BookVO [b_id=" + b_id + ", title=" + title + ", author=" + author + ", pubdate=" + pubdate
				+ ", description=" + description + ", isbn=" + isbn + ", cover=" + cover + ", publisher=" + publisher
				+ "]";
	}
	
}
