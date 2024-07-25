package com.openclassrooms.project.paymybuddy.controllers;

import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.service.TransferService;
import com.openclassrooms.project.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Objects;

import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.*;

@Controller
@RequiredArgsConstructor
public class TransferController
{
    private final UserService userService;

    private final TransferService transferService;


    @GetMapping("/transfer")
    public String loadTransferPage( Principal principal, Model model )
    {
        User connectedUser = userService.findByEmail( principal.getName( ) );

        model.addAttribute( "connections_list", transferService.getConnectionsUserList( connectedUser ) );
        model.addAttribute( "transactions_list", transferService.getTransferList( connectedUser ) );

        //returns the name of the html page of home
        return "transfer";
    }


    @PostMapping("/addConnection")
    public String addConnection( @RequestParam(name = "connectionEmail") String email, Principal principal, Model model )
    {
        User connectedUser = userService.findByEmail( principal.getName( ) );
        User addedUser = userService.findByEmail( email );
        String message = null;

        if( addedUser != null ){
            message = transferService.addConnection( connectedUser, addedUser );
        }
        else
        {
            model.addAttribute( UNKNOWN_USER_ERROR, UNKNOWN_USER_ERROR );
        }

        if( Objects.equals( message, TRANSACTION_ERROR ) )
        {
            model.addAttribute( TRANSACTION_ERROR, TRANSACTION_ERROR );
        }
        else if ( Objects.equals( message, EXISTING_USER_ERROR ) )
        {
            model.addAttribute( EXISTING_USER_ERROR, EXISTING_USER_ERROR );
        }
        else if ( Objects.equals( message, SUCCESSFUL_CONNECTION ) )
        {
            model.addAttribute( SUCCESSFUL_CONNECTION, SUCCESSFUL_CONNECTION );
        }

        return loadTransferPage( principal, model );
    }


    @PostMapping("/sendMoney")
    public String sendMoneyToConnection( @RequestParam String transactionConnectionEmail, @RequestParam(name = "transactionConnectionAmount") double transactionAmount, Principal principal, Model model )
    {
        User connectedUser = userService.findByEmail( principal.getName( ) );
        User connectionUser = userService.findByEmail( transactionConnectionEmail );
        String message = null;

        if( connectionUser != null )
        {
            message = transferService.sendMoneyToConnection( connectedUser, connectionUser, transactionAmount );
        }
        else
        {
            model.addAttribute( MISSING_CONNECTION, MISSING_CONNECTION );
        }

        if( Objects.equals( message, SUCCESSFUL_TRANSACTION ) )
        {
            model.addAttribute( SUCCESSFUL_TRANSACTION, SUCCESSFUL_TRANSACTION );
        }
        else if ( Objects.equals( message, TRANSACTION_ERROR ) )
        {
            model.addAttribute( TRANSACTION_ERROR, TRANSACTION_ERROR );
        }

        return loadTransferPage( principal, model );
    }
}
