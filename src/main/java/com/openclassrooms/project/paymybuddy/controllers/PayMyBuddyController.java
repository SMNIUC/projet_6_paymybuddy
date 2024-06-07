package com.openclassrooms.project.paymybuddy.controllers;

import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class PayMyBuddyController
{
    private final UserService userService;

    @GetMapping("/login")
    public String logIn()
    {
        //returns the name of the html page of home
        return "logIn";
    }

    @GetMapping("/home")
    public String payMyBuddyHome(Model model, Principal principal)
    {
        User user = userService.findByEmail(principal.getName( ) );
        model.addAttribute("connectedUser", user);

        //returns the name of the html page of home
        return "index";
    }


}
