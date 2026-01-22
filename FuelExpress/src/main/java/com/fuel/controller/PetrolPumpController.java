package com.fuel.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fuel.model.LoginUserBean;
import com.fuel.model.PetrolPump;
import com.fuel.service.PetrolPumpService;

@Controller
@SessionAttributes("LoginUserBean")
public class PetrolPumpController {

	@Autowired
	private PetrolPumpService petrolPumpService;

	@GetMapping("/registerpump")
	public String showForm(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("petrolPump", new PetrolPump());
		return "petrolpump/petrolform";
	}

	@PostMapping("/savepetrolpump")
	public String registerPump(@ModelAttribute PetrolPump petrolPump,
			@ModelAttribute("LoginUserBean") LoginUserBean lub, Model model) {
		try {
			petrolPumpService.registerPump(petrolPump);
			model.addAttribute("success", true);
		} catch (SQLException e) {
			e.printStackTrace();
			model.addAttribute("error", "Database error: " + e.getMessage());
		}
		return "petrolpump/petrolform";
	}

	// API Controller
	@GetMapping("/nearby-pumps")
	@ResponseBody
	public List<PetrolPump> nearbyPumps(
	        @RequestParam(name = "lat") double lat,
	        @RequestParam(name = "lng") double lng,
	        @RequestParam(name = "fuelType", required = false) String fuelType,
	        @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		System.out.println("Hello");
	    return petrolPumpService.findNearbyPumps(lat, lng, fuelType);
	}


//	@GetMapping("/pdashboard")
//	public String showppDashboard(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
//		model.addAttribute("registeredPumps", 5);
//		model.addAttribute("pendingApplications", 3);
//		model.addAttribute("activeCustomers", 1247);
//		model.addAttribute("monthlyRevenue", 284500);
//		model.addAttribute("userName", "Amit Jain");
//		model.addAttribute("userRole", "Pump Owner");
//		return "util/dashboard";
//	}

}
