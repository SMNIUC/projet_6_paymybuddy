package com.openclassrooms.project.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PayMyBuddyController
{
//    @GetMapping("/paymybuddy/login")
//    public String logIn()
//    {
//        //returns the name of the html page of home
//        return "logIn";
//    }

    @GetMapping("/home")
    public String payMyBuddyHomeUser( Model model )
    {
        model.addAttribute("firstName", "User");
        model.addAttribute("lastName", "Doe");
        //returns the name of the html page of home
        return "index";
    }
}
