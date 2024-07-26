package com.openclassrooms.project.paymybuddy.controllers;

import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.service.AccountService;
import com.openclassrooms.project.paymybuddy.service.TransferService;
import com.openclassrooms.project.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Objects;

import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.*;

@Controller
@RequiredArgsConstructor
public class AccountController
{
    private final AccountService accountService;

    private final UserService userService;

    private final TransferService transferService;


    @GetMapping("/profile")
    public String loadProfilePage( Principal principal, Model model )
    {
        User connectedUser = userService.findByEmail( principal.getName( ) );
        model.addAttribute( "connections_list", transferService.getConnectionsUserList( connectedUser ) );
        model.addAttribute( "user", connectedUser );

        //returns the name of the html page of home
        return "profile";
    }


    @PostMapping("/bankAccountTransfer")
    public String transferMoneyBetweenAccounts( @RequestParam String bankTransferAction, @RequestParam double bankTransferAmount, Principal principal, Model model )
    {
        User connectedUser = userService.findByEmail( principal.getName( ) );
        String userIban = connectedUser.getAccount( ).getIban( );
        String message;

        if( StringUtils.hasLength( userIban ) )
        {
            if( Objects.equals( bankTransferAction, "bankToAccount" ) )
            {
                message = accountService.doTransferBankToAccount( connectedUser, bankTransferAmount );

                if( Objects.equals( message, REFUSED_BANK_PAYMENT ) )
                {
                    model.addAttribute( REFUSED_BANK_PAYMENT, REFUSED_BANK_PAYMENT );
                }
                if( Objects.equals( message, SUCCESSFUL_TRANSFER ) )
                {
                    model.addAttribute( SUCCESSFUL_TRANSFER, SUCCESSFUL_TRANSFER );
                }
            }
            else if( Objects.equals( bankTransferAction, "accountToBank" ) )
            {
                message = accountService.doTransferAccountToBank( connectedUser, bankTransferAmount );

                if( Objects.equals( message, BALANCE_TOO_LOW ) )
                {
                    model.addAttribute( BALANCE_TOO_LOW, BALANCE_TOO_LOW );
                }
                if( Objects.equals( message, SUCCESSFUL_TRANSFER ) )
                {
                    model.addAttribute( SUCCESSFUL_TRANSFER, SUCCESSFUL_TRANSFER );
                }
            }
            else
            {
                model.addAttribute( SELECT_AN_ACTION, SELECT_AN_ACTION );
            }
        }
        else
        {
            model.addAttribute( NULL_IBAN_TRANSFER_ERROR, NULL_IBAN_TRANSFER_ERROR );
        }

        return loadProfilePage( principal, model );
    }

    @PostMapping("/ibanRegistration")
    public String registerUserIban( @RequestParam String iban, Principal principal, Model model )
    {
        User connectedUser = userService.findByEmail( principal.getName( ) );

        String message = accountService.registerUserIban( iban, connectedUser );

        if( Objects.equals( message, IBAN_SUCCESS ) )
        {
            model.addAttribute( IBAN_SUCCESS, IBAN_SUCCESS );
        }
        else if ( Objects.equals( message, NULL_IBAN_ERROR ) )
        {
            model.addAttribute( NULL_IBAN_ERROR, NULL_IBAN_ERROR );
        }

        return loadProfilePage( principal, model );
    }

}
