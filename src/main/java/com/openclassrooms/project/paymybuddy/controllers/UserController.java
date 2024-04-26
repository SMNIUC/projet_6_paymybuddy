package com.openclassrooms.project.paymybuddy.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController
{
    @GetMapping("/contact")
    public String loadContactPage()
    {
        //returns the name of the html page of home
        return "contact";
    }

}
