package org.ecommerce.bookreviewapp.model;

import java.util.Date;

import org.ecommerce.bookreviewapp.entity.Product;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ReviewInfo {
    private String code;
    private String name;
    private double price;
    private String tags;
    private String author;
    private String publisher;
    private String review;
    private String reviewDate;
    private Date reviewCreateDate;

    private boolean newProduct=false;

    private CommonsMultipartFile fileData;

    public ReviewInfo() {
    }

    public ReviewInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.tags = product.getTags();
    }

    public ReviewInfo(String code, String name, String review) {
        this.code = code;
        this.name = name;
        this.review = review;
    }

    public ReviewInfo(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public ReviewInfo(String code, String name, double price, String tags) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.tags = tags;
    }

    public ReviewInfo(String code, String name, double price, String tags, String author) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.tags = tags;
        this.author = author;
    }

    public ReviewInfo(String code, String name, double price, String tags, String author, String publisher) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.tags = tags;
        this.author = author;
        this.publisher = publisher;
    }

    public ReviewInfo(String name, String code, String review, String reviewDate) {
		this.code = code;
		this.name = name;
		this.review = review;
		this.reviewDate = reviewDate;
	}

	public ReviewInfo(String code, String name, String review, Date reviewCreateDate) {
		super();
		this.code = code;
		this.name = name;
		this.review = review;
		this.reviewCreateDate = reviewCreateDate;
	}

	public ReviewInfo(String code, String name, double price, String tags, String author, String publisher, String review) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.tags = tags;
        this.author = author;
        this.publisher = publisher;
        this.review = review;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public CommonsMultipartFile getFileData() {
        return fileData;
    }
    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }

    public boolean isNewProduct() {
        return newProduct;
    }
    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }

    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Date getReviewCreateDate() {
		return reviewCreateDate;
	}

	public void setReviewCreateDate(Date reviewCreateDate) {
		this.reviewCreateDate = reviewCreateDate;
	}
}
