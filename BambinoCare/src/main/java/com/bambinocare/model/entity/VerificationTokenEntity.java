package com.bambinocare.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="verification_token")
public class VerificationTokenEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "token_id")
	private Long tokenId;

	
	@Column(name = "token")
	private String token;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@Column(name = "expiry_date")
	private Date expiryDate;

	public VerificationTokenEntity() {
	}

	public VerificationTokenEntity(String token, UserEntity user) {
		super();
		this.token = token;
		this.user = user;
	}

	public VerificationTokenEntity(Long tokenId, String token, UserEntity user, Date expiryDate) {
		super();
		this.tokenId = tokenId;
		this.token = token;
		this.user = user;
		this.expiryDate = expiryDate;
	}

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
