package com.example.demo.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="limit-service")
public class LimitConfig {
	
	private Integer minimum;
	private Integer maximum;

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
	@Override
	public String toString() {
		return "LimitConfig [minimum=" + minimum + ", maximum=" + maximum + "]";
	}

}
