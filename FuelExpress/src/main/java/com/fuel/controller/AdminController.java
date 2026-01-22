package com.fuel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fuel.model.LoginUserBean;

@Controller
@SessionAttributes("LoginUserBean")
public class AdminController {

	@GetMapping("/adashboard")
	public String showadDashboard(Model model, @ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("registeredPumps", 5);
		model.addAttribute("pendingApplications", 3);
		model.addAttribute("activeCustomers", 1247);
		model.addAttribute("monthlyRevenue", 284500);
		System.out.println("lub.getLoginname();" + lub.getLoginname());
		return "admin/dashboard";
	}

}
