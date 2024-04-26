package com.openclassrooms.project.paymybuddy.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AccountController
{
    @GetMapping("/profile")
    public String loadProfilePage()
    {
        //returns the name of the html page of home
        return "profile";
    }
}
