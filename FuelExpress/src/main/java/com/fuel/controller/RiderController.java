package com.fuel.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuel.model.FuelBooking;
import com.fuel.model.LoginUserBean;
import com.fuel.model.OrderFuel;
import com.fuel.model.Rider;
import com.fuel.service.RiderService;

@Controller
@SessionAttributes("LoginUserBean")
public class RiderController {

	@Autowired
	private RiderService riderService;

//	@GetMapping("/riderregister")
//	public String showRiderForm(Model model) {
//		model.addAttribute("rider", new Rider());
//		return "rider/riderform";
//	}

	@GetMapping("/riderlogin")
	public String login() {
		return "rider/riderlogin";
	}

	@GetMapping("/riderregister")
	public String showRegistrationForm(Model model) {
		model.addAttribute("rider", new Rider());
		model.addAttribute("states", List.of("Andhra Pradesh", "Telangana", "Odisha", "Maharashtra", "Karnataka"));
		return "rider/riderform";
	}

	@PostMapping("/register")
	public String registerRider(@ModelAttribute("rider") Rider rider, RedirectAttributes redirectAttributes) {
		riderService.registerRider(rider);
		return "redirect:/riderlogin";
	}

	@GetMapping("/rdashboard")
	public String riderDashboard(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {

		model.addAttribute("orders", List.of());
		model.addAttribute("hasOrders", false);

		return "rider/riderdashboard";
	}

	@GetMapping("/refresh")
	public String refreshOrders(@RequestParam BigDecimal riderLat, @RequestParam BigDecimal riderLng, Model model)
			throws Exception {
		List<OrderFuel> orders = riderService.getAvailableOrders(riderLat, riderLng);
		model.addAttribute("orders", orders);
		return "rider/riderdashboard";
	}

	@PostMapping("/accept-order")
	public String acceptOrder(@RequestParam("bookingId") Long bookingId, RedirectAttributes redirectAttributes,
			@ModelAttribute("LoginUserBean") LoginUserBean lub) {

		int riderId = lub.getLoginriderid();
		riderService.acceptOrder(bookingId, riderId);
		return "redirect:/rider/dashboard";
	}

	@GetMapping("/orders")
	public String viewRiderOrders(
			@ModelAttribute("LoginUserBean") LoginUserBean lub, Model model) throws SQLException {
		int loginriderid = lub.getLoginriderid();
		List<FuelBooking> orders = riderService.getOrdersByRiderId(loginriderid);
		model.addAttribute("orders", orders);
		return "rider/myorders";
	}

}
