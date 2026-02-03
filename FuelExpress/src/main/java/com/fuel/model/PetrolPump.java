package com.fuel.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetrolPump {

	private int id;
    private String name;            
    private String ownerName;
    private String contactNumber;
    private String email;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String fuelTypes;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;
    private Double distance;

}
