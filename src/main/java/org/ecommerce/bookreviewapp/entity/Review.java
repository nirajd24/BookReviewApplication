package org.ecommerce.bookreviewapp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Product_Reviews")
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String account;
	private String product;
	private Date createDate;
	private String review;

	@Id
    @Column(name = "ID", length = 50, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "USER_NAME", nullable = false)
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "PRODUCT_ID", nullable = false)
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date", nullable = false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "Reviews", nullable = false)
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
}
