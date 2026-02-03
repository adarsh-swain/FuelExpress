package com.fuel.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuel.model.Customer;
import com.fuel.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void saveCustomer(Customer customer) {

	    String sql = """
	        INSERT INTO customer
	        (full_name, contact_number, email, password, address, city, state, pincode, referral_code)
	        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
	        """;

	    try (Connection con = dataSource.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, customer.getFullName());
	        ps.setString(2, customer.getPhone());
	        ps.setString(3, customer.getEmail());
	        ps.setString(4, customer.getPassword());
	        ps.setString(5, customer.getAddress());
	        ps.setString(6, customer.getCity());
	        ps.setString(7, customer.getState());
	        ps.setString(8, customer.getPincode());
	        ps.setString(9, customer.getReferralCode());

	        ps.executeUpdate();

	    } catch (SQLException e) {
	        throw new RuntimeException("Error saving customer", e);
	    }
	}


	@Override
	public List<Customer> getAllCustomers() {

	    List<Customer> list = new ArrayList<>();

	    String sql = """
	        SELECT id, full_name, contact_number, email, password,
	               address, city, state, pincode, referral_code
	        FROM customer
	        """;

	    try (Connection con = dataSource.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {

	            Customer c = new Customer();

	            c.setId(rs.getInt("id"));
	            c.setFullName(rs.getString("full_name"));
	            c.setPhone(rs.getString("contact_number"));
	            c.setEmail(rs.getString("email"));
	            c.setPassword(rs.getString("password"));
	            c.setAddress(rs.getString("address"));
	            c.setCity(rs.getString("city"));
	            c.setState(rs.getString("state"));
	            c.setPincode(rs.getString("pincode"));
	            c.setReferralCode(rs.getString("referral_code"));

	            list.add(c);
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error fetching customers", e);
	    }

	    return list;
	}


}
