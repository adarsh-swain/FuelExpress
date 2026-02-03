package com.fuel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelBooking {

	private Integer bookingId;
	private Integer customerId;
	private Integer pumpId;

	private String pumpName;
	private String fuelType;

	private Double quantity;
	private String vehicleNumber;
	private String deliveryTimeSlot;
	private String deliveryAddress;
	private String paymentMethod;

	private Double baseCost;
	private Double deliveryCharge;
	private Double tax;
	private Double totalAmount;

	private String status;
	private Double latitude;
	private Double longitude;
	
	private String riderStatus;
	private String riderId;
	private String orderDate;

}
