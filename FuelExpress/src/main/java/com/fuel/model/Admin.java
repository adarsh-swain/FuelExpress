package com.fuel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
	
	private int adminId;
    private String name;
    private String email;
    private String password;
	
}
