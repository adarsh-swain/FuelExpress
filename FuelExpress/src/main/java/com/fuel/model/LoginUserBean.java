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

}
