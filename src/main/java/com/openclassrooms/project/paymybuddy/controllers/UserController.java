package com.openclassrooms.project.paymybuddy.controllers;

import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @GetMapping("/contact")
    public String loadContactPage( Principal principal, Model model )
    {
        User user = userService.findByEmail( principal.getName( ) );
        model.addAttribute( "connectedUser", user );

        //returns the name of the html page of home
        return "contact";
    }

}
