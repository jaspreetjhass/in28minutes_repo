package com.example.demo.models;

public class LimitResult {

	private Integer minimum;
	private Integer maximum;
	private Long port;

	public LimitResult() {

	}

	public LimitResult(Integer minimum, Integer maximum, Long port) {
		super();
		this.minimum = minimum;
		this.maximum = maximum;
		this.port = port;
	}

	public Integer getMinimum() {
		return minimum;
	}

	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}

	public Integer getMaximum() {
		return maximum;
	}

	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}

	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "LimitResult [minimum=" + minimum + ", maximum=" + maximum + ", port=" + port + "]";
	}

}
