package com.openclassrooms.project.paymybuddy.controllers;

import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Objects;

import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.EXISTING_EMAIL_ERROR;
import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.SUCCESSFUL_REGISTRATION;

@RequiredArgsConstructor
@Controller
public class PayMyBuddyController
{
    private final UserService userService;

    @GetMapping("/login")
    public String logIn( @RequestParam( required = false ) Boolean error, Model model ) //param name defaults to var name
    {
        if ( error != null && error )
        {
            model.addAttribute( "error", true );
        }

        return "logIn";
    }

    @GetMapping("/home")
    public String payMyBuddyHome( Model model, Principal principal )
    {
        User user = userService.findByEmail( principal.getName( ) );
        model.addAttribute( "connectedUser", user );

        //returns the name of the html page of home
        return "index";
    }

    @PostMapping("/registration")
    public String createNewAccount( @RequestParam String username, @RequestParam String password, Model model )
    {
        String message = userService.registerNewUser( username, password );

        if( Objects.equals( message, SUCCESSFUL_REGISTRATION ) )
        {
            model.addAttribute( SUCCESSFUL_REGISTRATION, SUCCESSFUL_REGISTRATION );
        }
        else if ( Objects.equals( message, EXISTING_EMAIL_ERROR ) )
        {
            model.addAttribute( EXISTING_EMAIL_ERROR, EXISTING_EMAIL_ERROR );
        }

        return logIn( false, model );
    }
}
