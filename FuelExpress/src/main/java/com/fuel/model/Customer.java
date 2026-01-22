package com.fuel.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	private int id;
	private String fullName;
	private LocalDate dateOfBirth;
	private String gender;
	private String email;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String referralCode;
	private String phone;
	private String password;

}
