package com.fuel.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rider {

	private String fullName;
	private LocalDate dateOfBirth;
	private String gender;
	private String contactNumber;
	private String email;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String emergencyContact;

	// Vehicle Information
	private String vehicleType;
	private String vehicleBrand;
	private String vehicleModel;
	private Integer vehicleYear;
	private String vehicleNumber;
	private String licenseNumber;
	private LocalDate licenseExpiry;

	// Service Preferences
	private String serviceType;
	private String availability;
	private String areasCovered;

}
