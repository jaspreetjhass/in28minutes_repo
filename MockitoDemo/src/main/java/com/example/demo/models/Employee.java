package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Employee class used to store employee related information")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(accessMode = AccessMode.READ_ONLY, name = "EmployeeId", required = true)
	private Integer empId;
	@ApiModelProperty(accessMode = AccessMode.READ_WRITE, name = "EmployeeName", required = true)
	private String empName;
	@ApiModelProperty(accessMode = AccessMode.READ_WRITE, name = "EmployeeAddress", required = true)
	private String address;

}
