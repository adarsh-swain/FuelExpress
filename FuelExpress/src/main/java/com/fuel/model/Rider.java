package com.fuel.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rider {

	private int id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String contactNumber;
    private String email;
    private String emergencyContact;
    private String gender;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String riderStatus;

    private String vehicleType;
    private String vehicleBrand;
    private String vehicleModel;
    private Integer vehicleYear;
    private String vehicleNumber;

    private String licenseNumber;
    private LocalDate licenseExpiry;

    private String serviceType;
    private String availability;
    private String areasCovered;
    
    private String profileImageName;
    private String profileImagePath;
    private String profileImageType;
    private String password;

    private int verified;

}
