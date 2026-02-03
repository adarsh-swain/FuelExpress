package com.fuel.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.fuel.model.FuelBooking;
import com.fuel.model.OrderFuel;
import com.fuel.model.Rider;

public interface RiderService {

	void registerRider(Rider rider);

	public List<OrderFuel> getAvailableOrders(BigDecimal riderLatitude, BigDecimal riderLongitude) throws Exception;

	void acceptOrder(Long bookingId, int riderId);

	List<FuelBooking> getOrdersByRiderId(int riderId) throws SQLException;
}
