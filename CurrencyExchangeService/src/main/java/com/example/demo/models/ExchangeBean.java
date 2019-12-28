package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ExchangeBean {

	@Id
	@GeneratedValue
	private Integer exchangeId;
	@Column(name="currencyFrom")
	private String from;
	@Column(name="currencyTo")
	private String to;
	private Double conversionFactor;
	@Transient
	private Long port;
	
	public ExchangeBean() {
		
	}
	
	public ExchangeBean(Integer exchangeId, String from, String to, Double conversionFactor, Long port) {
		super();
		this.exchangeId = exchangeId;
		this.from = from;
		this.to = to;
		this.conversionFactor = conversionFactor;
		this.port = port;
	}

	public Integer getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(Integer exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Double getConversionFactor() {
		return conversionFactor;
	}
	public void setConversionFactor(Double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	public Long getPort() {
		return port;
	}
	public void setPort(Long port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "ExchangeBean [from=" + from + ", to=" + to + ", conversionFactor=" + conversionFactor + ", port=" + port
				+ "]";
	}
	
}
