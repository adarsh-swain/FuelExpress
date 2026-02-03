package com.fuel.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserBean {

	private int loginadminId;
	private String loginname;
	private String loginemail;
	private int logincustomerid;
	private String logincustomername;
	private LocalDate logindob;
	private String logingender;
	private String loginaddress;
	private String logincity;
	private String loginstate;
	private String loginpincode;
	private String referralCode;
	private String loginphone;
	
	private int loginriderid;
    private String loginriderfullName;
    private LocalDate loginriderdateOfBirth;
    private String loginridercontactNumber;
    private String loginrideremail;
    private String loginrideremergencyContact;
    private String loginridergender;
    private String loginrideraddress;
    private String loginridercity;
    private String loginriderstate;
    private String loginriderpincode;

    private String loginridervehicleType;
    private String loginridervehicleBrand;
    private String loginridervehicleModel;
    private Integer loginridervehicleYear;
    private String loginridervehicleNumber;

    private String loginriderlicenseNumber;
    private LocalDate loginriderlicenseExpiry;

}
