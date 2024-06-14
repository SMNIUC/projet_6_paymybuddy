package com.openclassrooms.project.paymybuddy.controllers;

import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.service.AccountService;
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
public class AccountController
{
    private final AccountService accountService;

    private final UserService userService;

    @GetMapping("/profile")
    public String loadProfilePage()
    {
        //returns the name of the html page of home
        return "profile";
    }

    @PostMapping("/profile")
    public void transferMoneyBetweenAccounts( @RequestParam(name = "bankTransferAction") String bankTransferAction, @RequestParam(name = "bankTransferAmount") double bankTransferAmount, Principal principal, Model model )
    {
        User connectedUser = userService.findByEmail( principal.getName( ) );
        String message;

        if( Objects.equals( bankTransferAction, "bankToAccount" ) )
        {
            message = accountService.doTransferBankToAccount( connectedUser, bankTransferAmount );

            if( Objects.equals( message, REFUSED_BANK_PAYMENT ) )
            {
                model.addAttribute( REFUSED_BANK_PAYMENT, REFUSED_BANK_PAYMENT);
            }
            if( Objects.equals( message, SUCCESSFUL_TRANSFER ) )
            {
                model.addAttribute( SUCCESSFUL_TRANSFER, SUCCESSFUL_TRANSFER);
            }
        }
        else if( Objects.equals( bankTransferAction, "accountToBank" ) )
        {
            message = accountService.doTransferAccountToBank( connectedUser, bankTransferAmount );

            if( Objects.equals( message, BALANCE_TOO_LOW ) )
            {
                model.addAttribute( BALANCE_TOO_LOW, BALANCE_TOO_LOW);
            }
            if( Objects.equals( message, SUCCESSFUL_TRANSFER ) )
            {
                model.addAttribute( SUCCESSFUL_TRANSFER, SUCCESSFUL_TRANSFER);
            }
        }
        else
        {
            model.addAttribute( SELECT_AN_ACTION, SELECT_AN_ACTION);
        }
    }
}
