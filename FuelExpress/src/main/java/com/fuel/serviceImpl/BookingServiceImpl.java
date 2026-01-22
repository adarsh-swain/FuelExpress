package com.fuel.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuel.model.FuelBooking;
import com.fuel.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private DataSource dataSource;

	@Override
	public FuelBooking saveBooking(FuelBooking booking) throws SQLException {
		String sql = "INSERT INTO fuel_bookings (customer_id, pump_id, pump_name, fuel_type, quantity, vehicle_number, delivery_time_slot, delivery_address, payment_method, base_cost, delivery_charge, tax, total_amount, status, latitude, longitude) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setLong(1, booking.getCustomerId());
			ps.setLong(2, booking.getPumpId());
			ps.setString(3, booking.getPumpName());
			ps.setString(4, booking.getFuelType());
			ps.setDouble(5, booking.getQuantity());
			ps.setString(6, booking.getVehicleNumber());
			ps.setString(7, booking.getDeliveryTimeSlot());
			ps.setString(8, booking.getDeliveryAddress());
			ps.setString(9, booking.getPaymentMethod());
			ps.setDouble(10, booking.getBaseCost());
			ps.setDouble(11, booking.getDeliveryCharge());
			ps.setDouble(12, booking.getTax());
			ps.setDouble(13, booking.getTotalAmount());
			ps.setString(14, booking.getStatus());
			ps.setDouble(15, booking.getLatitude());
			ps.setDouble(16, booking.getLongitude());

			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					booking.setBookingId(rs.getLong(1));
				}
			}
		}

		return booking;
	}

}
