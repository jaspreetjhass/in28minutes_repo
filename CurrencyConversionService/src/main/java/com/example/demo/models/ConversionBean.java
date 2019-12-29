package com.example.demo.models;

public class ConversionBean {

	private Integer conversionId;
	private String from;
	private String to;
	private Double conversionFactor;
	private Double amount;
	private Long port;
	
	public ConversionBean() {
		
	}
	
	public ConversionBean(Integer conversionId, String from, String to, Double conversionFactor, Double amount,
			Long port) {
		super();
		this.conversionId = conversionId;
		this.from = from;
		this.to = to;
		this.conversionFactor = conversionFactor;
		this.amount = amount;
		this.port = port;
	}

	public Integer getConversionId() {
		return conversionId;
	}

	public void setConversionId(Integer conversionId) {
		this.conversionId = conversionId;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "ConversionBean [conversionId=" + conversionId + ", from=" + from + ", to=" + to + ", conversionFactor="
				+ conversionFactor + ", amount=" + amount + ", port=" + port + "]";
	}
	
}
