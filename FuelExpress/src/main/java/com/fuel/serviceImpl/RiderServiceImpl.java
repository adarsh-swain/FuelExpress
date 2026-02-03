package com.fuel.serviceImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuel.model.Customer;
import com.fuel.model.FuelBooking;
import com.fuel.model.OrderFuel;
import com.fuel.model.OrderFuel.OrderStatus;
import com.fuel.model.Rider;
import com.fuel.service.RiderService;

@Service
public class RiderServiceImpl implements RiderService {

	@Autowired
	private DataSource dataSource;

	@Override
	public void registerRider(Rider rider) {

		String sql = """
				    INSERT INTO riders (
				        full_name, date_of_birth, contact_number, email,
				        emergency_contact, gender, address, city, state, pincode,
				        vehicle_type, vehicle_brand, vehicle_model, vehicle_year, vehicle_number,
				        license_number, license_expiry,
				        profile_image_name, profile_image_path, profile_image_type,
				        service_type, availability, areas_covered,password
				    )
				    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)
				""";

		try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, rider.getFullName());
			ps.setDate(2, Date.valueOf(rider.getDateOfBirth()));
			ps.setString(3, rider.getContactNumber());
			ps.setString(4, rider.getEmail());
			ps.setString(5, rider.getEmergencyContact());
			ps.setString(6, rider.getGender());
			ps.setString(7, rider.getAddress());
			ps.setString(8, rider.getCity());
			ps.setString(9, rider.getState());
			ps.setString(10, rider.getPincode());

			ps.setString(11, rider.getVehicleType());
			ps.setString(12, rider.getVehicleBrand());
			ps.setString(13, rider.getVehicleModel());
			ps.setInt(14, rider.getVehicleYear());
			ps.setString(15, rider.getVehicleNumber());

			ps.setString(16, rider.getLicenseNumber());
			ps.setDate(17, Date.valueOf(rider.getLicenseExpiry()));

			// Image fields
			ps.setString(18, rider.getProfileImageName());
			ps.setString(19, rider.getProfileImagePath());
			ps.setString(20, rider.getProfileImageType());

			ps.setString(21, rider.getServiceType());
			ps.setString(22, rider.getAvailability());
			ps.setString(23, rider.getAreasCovered());
			ps.setString(24, rider.getPassword());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("Failed to register rider", e);
		}
	}

	@Override
	public List<OrderFuel> getAvailableOrders(BigDecimal riderLatitude, BigDecimal riderLongitude) throws Exception {

		List<OrderFuel> orders = new ArrayList<>();

		String sql = """
				SELECT *
				FROM (
				    SELECT
				        b.booking_id,
				        b.customer_id,
				        b.pump_id,
				        b.pump_name,
				        b.fuel_type,
				        b.quantity,
				        b.total_amount,
				        b.vehicle_number,
				        b.delivery_address,
				        b.status,
				        b.order_date,
				        b.delivery_time_slot,
				        b.payment_method,
				        b.latitude,
				        b.longitude,

				        c.id AS c_id,
				        c.full_name,
				        c.contact_number,

				        (
				            6371 * acos(
				                cos(radians(?)) *
				                cos(radians(b.latitude)) *
				                cos(radians(b.longitude) - radians(?)) +
				                sin(radians(?)) *
				                sin(radians(b.latitude))
				            )
				        ) AS distance
				    FROM fuel_bookings b
				    JOIN customer c ON b.customer_id = c.id
				    WHERE b.latitude IS NOT NULL
				      AND b.longitude IS NOT NULL
				      AND (b.riderstatus IS NULL OR b.riderstatus <> 'CONFIRMED')
				) t
				WHERE distance <= 10
				ORDER BY distance ASC
				LIMIT 1
				""";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setBigDecimal(1, riderLatitude);
			ps.setBigDecimal(2, riderLongitude);
			ps.setBigDecimal(3, riderLatitude);
			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					OrderFuel order = new OrderFuel();
					order.setId(rs.getLong("booking_id"));
					order.setCustomerId(rs.getLong("customer_id"));
					order.setPumpId(rs.getLong("pump_id"));
					order.setFuelType(rs.getString("fuel_type"));
					order.setQuantity(rs.getBigDecimal("quantity"));
					order.setTotalAmount(rs.getBigDecimal("total_amount"));
					order.setVehicleNumber(rs.getString("vehicle_number"));
					order.setDeliveryAddress(rs.getString("delivery_address"));
					order.setStatus(OrderStatus.valueOf(rs.getString("status")));
					order.setOrderDate(rs.getTimestamp("order_date"));
					order.setPaymentMethod(rs.getString("payment_method"));
					order.setCustomerLatitude(rs.getBigDecimal("latitude"));
					order.setCustomerLongitude(rs.getBigDecimal("longitude"));
					Customer customer = new Customer();
					customer.setId(rs.getInt("c_id"));
					customer.setFullName(rs.getString("full_name"));
					customer.setPhone(rs.getString("contact_number"));
					order.setCustomer(customer);

					double distance = rs.getDouble("distance");
					order.setDistance(distance);

					DecimalFormat df = new DecimalFormat("#.#");
					order.setDistanceFormatted(df.format(distance));

					orders.add(order);
				}
			}
		}

		return orders;
	}

	@Override
	public void acceptOrder(Long bookingId, int riderId) {

		String sql = """
				UPDATE fuel_bookings
				SET riderstatus = 'CONFIRMED',
				    rider_id = ?
				WHERE booking_id = ?
				  AND (riderstatus IS NULL OR riderstatus <> 'CONFIRMED')
				""";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setLong(1, riderId);
			ps.setLong(2, bookingId);

			int updated = ps.executeUpdate();
			if (updated == 0) {
				throw new RuntimeException("Order already confirmed or not found");
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed to accept order", e);
		}
	}

	@Override
	public List<FuelBooking> getOrdersByRiderId(int riderId) throws SQLException {

		List<FuelBooking> orders = new ArrayList<>();

		String sql = "SELECT * FROM fuel_bookings WHERE rider_id = ? ORDER BY order_date DESC";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, riderId);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					FuelBooking booking = new FuelBooking();

					booking.setBookingId(rs.getInt("booking_id"));
					booking.setCustomerId(rs.getInt("customer_id"));
					booking.setPumpId(rs.getInt("pump_id"));
					booking.setPumpName(rs.getString("pump_name"));
					booking.setFuelType(rs.getString("fuel_type"));
					booking.setQuantity(rs.getDouble("quantity"));
					booking.setVehicleNumber(rs.getString("vehicle_number"));
					booking.setDeliveryTimeSlot(rs.getString("delivery_time_slot"));
					booking.setDeliveryAddress(rs.getString("delivery_address"));
					booking.setPaymentMethod(rs.getString("payment_method"));
					booking.setBaseCost(rs.getDouble("base_cost"));
					booking.setDeliveryCharge(rs.getDouble("delivery_charge"));
					booking.setTax(rs.getDouble("tax"));
					booking.setTotalAmount(rs.getDouble("total_amount"));
					booking.setStatus(rs.getString("status"));
					booking.setRiderStatus(rs.getString("riderstatus"));
					booking.setRiderId(rs.getString("rider_id"));
					booking.setOrderDate(rs.getString("order_date"));

					orders.add(booking);
				}
			}
		}
		return orders;
	}

}
