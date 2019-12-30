package com.example.demo.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NameResource {

	// uri versioning start
	@GetMapping(path = { "/nameV1" }, produces = "text/plain")
	public String getNameV1() {
		return "jaspreet singh";
	}

	@GetMapping(path = { "/nameV2" })
	public Name getNameV2() {
		return new Name("jaspreet", "singh");
	}
	// uri versioning end

	// parameter versioning start
	@GetMapping(path = { "/nameParamVersion" }, produces = "text/plain", params = "version=1")
	public String getNameP1() {
		return "jaspreet singh";
	}

	@GetMapping(path = { "/nameParamVersion" }, params = "version=2")
	public Name getNameP2() {
		return new Name("jaspreet", "singh");
	}
	// parameter versioning end

	// header versioning start
	@GetMapping(path = { "/nameHeaderVersion" }, produces = "text/plain", headers = "version=1")
	public String getNameH1() {
		return "jaspreet singh";
	}

	@GetMapping(path = { "/nameHeaderVersion" }, headers = "version=2")
	public Name getNameH2() {
		return new Name("jaspreet", "singh");
	}
	// header versioning end

	
	// media type or mine versioning start
	@GetMapping(path = { "/nameMediaVersion" }, produces = "text/version1+plain")
	public String getNameM1() {
		return "jaspreet singh";
	}

	@GetMapping(path = { "/nameMediaVersion" }, produces = "application/version2+json")
	public Name getNameM2() {
		return new Name("jaspreet", "singh");
	}
	// media type or mine versioning end
}
