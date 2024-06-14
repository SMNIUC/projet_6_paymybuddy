package com.openclassrooms.project.paymybuddy.service;

import com.openclassrooms.project.paymybuddy.model.Account;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.*;

@Service
@RequiredArgsConstructor
public class AccountService
{
    private final AccountRepository accountRepository;

    @Transactional
    public String doTransferBankToAccount( User connectedUser, double transferAmount )
    {
        Account userAccount = connectedUser.getAccountId( );
        double userAccountBalance = userAccount.getAccountBalance( );
        double userBankBalance = userAccount.getBankBalance( );
        String message = null;

        if( userBankBalance >= transferAmount )
        {
            userAccountBalance += transferAmount;
            userBankBalance -= transferAmount;

            userAccount.setAccountBalance( userAccountBalance );
            userAccount.setBankBalance( userBankBalance );
            accountRepository.save( userAccount );

            message = SUCCESSFUL_TRANSFER;
        }
        else
        {
            message = REFUSED_BANK_PAYMENT;
        }
        return message;
    }

    // Method to simulate call to bank
    @Transactional
    public String doTransferAccountToBank( User connectedUser, double transferAmount )
    {
        Account userAccount = connectedUser.getAccountId( );
        double userAccountBalance = userAccount.getAccountBalance( );
        double userBankBalance = userAccount.getBankBalance( );
        String message;

        if( userAccountBalance >= transferAmount )
        {
            userAccountBalance -= transferAmount;
            userBankBalance += transferAmount;

            userAccount.setAccountBalance( userAccountBalance );
            userAccount.setBankBalance( userBankBalance );
            accountRepository.save( userAccount );

            message = SUCCESSFUL_TRANSFER;
        }
        else
        {
            message = BALANCE_TOO_LOW;
        }
        return message;
    }

}
