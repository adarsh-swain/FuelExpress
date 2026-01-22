package com.fuel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fuel.model.Rider;

@Controller
@SessionAttributes("LoginUserBean")
public class RiderController {
	
	@GetMapping("/riderregister")
    public String showRiderForm(Model model) {
        model.addAttribute("rider", new Rider());
        return "rider/riderform";
    }
	
	@GetMapping("/riderlogin")
	public String login() {
		return "rider/riderlogin";
	}

}
