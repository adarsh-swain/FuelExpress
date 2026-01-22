package com.fuel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fuel.model.LoginUserBean;

@Controller
@SessionAttributes("LoginUserBean")
public class BookingController {
	
	
	@GetMapping("/bookingdash")
	public String dashboard(Model model,@ModelAttribute("LoginUserBean") LoginUserBean lub) {
		model.addAttribute("defaultLat", 20.2961);
		model.addAttribute("defaultLng", 85.8245);
		return "customer/bookfuel";
	}

}
