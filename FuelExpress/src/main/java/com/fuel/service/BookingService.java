package com.fuel.service;

import java.sql.SQLException;

import com.fuel.model.FuelBooking;

public interface BookingService {
	
	
	public FuelBooking saveBooking(FuelBooking booking) throws SQLException;

}
