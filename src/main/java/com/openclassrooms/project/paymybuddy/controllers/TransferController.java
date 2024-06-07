package com.openclassrooms.project.paymybuddy.controllers;

import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.service.ConnectionService;
import com.openclassrooms.project.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.*;

@Controller
@RequiredArgsConstructor
public class TransferController
{
    private final UserService userService;

    private final ConnectionService connectionService;

    @GetMapping("/transfer")
    public String loadTransferPage( Model model )
    {
        // List of all users to add to connection list
        List<User> allUsersList = userService.getAllUsers();
        model.addAttribute( "allUsersList", allUsersList);

        // List of all connections to select from to make a transfer
//        List<User> connectionsList = userService.getConnectionsList();
//        model.addAttribute("connectionsList", connectionsList);

        //returns the name of the html page of home
        return "transfer";
    }

    @PostMapping("/transfer")
    public void addConnection(@RequestParam(name = "connectionEmail") String email, Principal principal, Model model )
    {
        User connectedUser = userService.findByEmail( principal.getName( ) );
        User addedUser = userService.findByEmail( email );
        String message = null;

        if( addedUser != null ){
            message = connectionService.addConnection( connectedUser, addedUser );
        }
        else
        {
            model.addAttribute( UNKNOWN_USER_ERROR, UNKNOWN_USER_ERROR);
        }

        if( Objects.equals( message, UNKNOWN_USER_ERROR ) )
        {
            model.addAttribute( UNKNOWN_USER_ERROR, UNKNOWN_USER_ERROR);
        }
        else if ( Objects.equals( message, EXISTING_USER_ERROR ) )
        {
            model.addAttribute( EXISTING_USER_ERROR, EXISTING_USER_ERROR);
        }
        else if ( Objects.equals( message, SUCCESSFUL_CONNECTION ) )
        {
            model.addAttribute( SUCCESSFUL_CONNECTION, SUCCESSFUL_CONNECTION);
        }
    }
}
