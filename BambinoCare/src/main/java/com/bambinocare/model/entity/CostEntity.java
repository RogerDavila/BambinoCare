package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cost")
public class CostEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cost_id",nullable=false, unique=true)
	private Integer costId;
	
	@Column(name="bambino_quantity", nullable=false)
	private Integer bambinoQuantity;
	
	@Column(name="hour_quantity", nullable=false)
	private Double hourQuantity;
	
	@Column(name="cost", nullable=false)
	private Double cost;

	public CostEntity(Integer costId, Integer bambinoQuantity, Double hourQuantity, Double cost) {
		super();
		this.costId = costId;
		this.bambinoQuantity = bambinoQuantity;
		this.hourQuantity = hourQuantity;
		this.cost = cost;
	}
	
	public CostEntity() {
	}

	public Integer getCostId() {
		return costId;
	}

	public void setCostId(Integer costId) {
		this.costId = costId;
	}

	public Integer getBambinoQuantity() {
		return bambinoQuantity;
	}

	public void setBambinoQuantity(Integer bambinoQuantity) {
		this.bambinoQuantity = bambinoQuantity;
	}

	public Double getHourQuantity() {
		return hourQuantity;
	}

	public void setHourQuantity(Double hourQuantity) {
		this.hourQuantity = hourQuantity;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	
}
