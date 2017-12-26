package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parameter")
public class ParameterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parameter_id", nullable = false, unique = true)
	private Integer parameterId;

	@Column(name = "parameter_key", nullable = false)
	private String parameterKey;

	@Column(name = "parameter_value", nullable = false)
	private String parameterValue;

	public ParameterEntity(Integer parameterId, String parameterKey, String parameterValue) {
		super();
		this.parameterId = parameterId;
		this.parameterKey = parameterKey;
		this.parameterValue = parameterValue;
	}

	public ParameterEntity() {
	}

	public Integer getParameterId() {
		return parameterId;
	}

	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}

	public String getParameterKey() {
		return parameterKey;
	}

	public void setParameterKey(String parameterKey) {
		this.parameterKey = parameterKey;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

}
