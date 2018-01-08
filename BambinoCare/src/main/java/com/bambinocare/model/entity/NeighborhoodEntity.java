package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="neighborhood")
public class NeighborhoodEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "neighborhood_id", unique = true, nullable = false)
	private Integer neighborhoodId;

	@Column(name = "neighborhood_desc", nullable = false)
	private String neighborhoodDesc;
	
	@JoinColumn(name="city_id", nullable=false)
	@OneToOne(fetch = FetchType.EAGER)
	private CityEntity city;

	public NeighborhoodEntity(Integer neighborhoodId, String neighborhoodDesc, CityEntity city) {
		super();
		this.neighborhoodId = neighborhoodId;
		this.neighborhoodDesc = neighborhoodDesc;
		this.city = city;
	}

	public NeighborhoodEntity() {
	}

	public Integer getNeighborhoodId() {
		return neighborhoodId;
	}

	public void setNeighborhoodId(Integer neighborhoodId) {
		this.neighborhoodId = neighborhoodId;
	}

	public String getNeighborhoodDesc() {
		return neighborhoodDesc;
	}

	public void setNeighborhoodDesc(String neighborhoodDesc) {
		this.neighborhoodDesc = neighborhoodDesc;
	}

	public CityEntity getCity() {
		return city;
	}

	public void setCity(CityEntity city) {
		this.city = city;
	}
	
}
