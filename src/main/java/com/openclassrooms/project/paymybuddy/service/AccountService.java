package com.openclassrooms.project.paymybuddy.service;

import com.openclassrooms.project.paymybuddy.model.Account;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.*;

@Service
@RequiredArgsConstructor
public class AccountService
{
    private final AccountRepository accountRepository;

    /**
     * Creates and saves a new account object in the database
     *
     * @return Account object
     */
    @Transactional
    public Account createNewAccount( )
    {
        Account newAccount = new Account( );
        newAccount.setCreationDate( new Date( ) );

        accountRepository.save( newAccount );

        return newAccount;
    }

    /**
     * Saves the user's iban in the database
     *
     * @param iban
     * @param connectedUser
     * @return validation/error message
     */
    @Transactional
    public String registerUserIban( String iban, User connectedUser )
    {
        String message;

        if( StringUtils.hasLength( iban ) )
        {
            Account userAccount = connectedUser.getAccount( );
            userAccount.setIban( iban );
            accountRepository.save( userAccount );
            message = IBAN_SUCCESS;
        }
        else
        {
            message = NULL_IBAN_ERROR;
        }

        return message;
    }

    /**
     * Simulates banking transfers
     * Transfers a specified amount from the connected user's
     * bank account to their paymybuddy account
     *
     * @param connectedUser
     * @param transferAmount
     * @return validation/error message
     */
    @Transactional
    public String doTransferBankToAccount( User connectedUser, double transferAmount )
    {
        Account userAccount = connectedUser.getAccount( );
        double userAccountBalance = userAccount.getAccountBalance( );
        double userBankBalance = userAccount.getBankBalance( );
        String message;

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

    /**
     * Simulates banking transfers
     * Transfers a specified amount from the connected user's
     * paymybuddy account to their bank account
     *
     * @param connectedUser
     * @param transferAmount
     * @return validation/error message
     */
    @Transactional
    public String doTransferAccountToBank( User connectedUser, double transferAmount )
    {
        Account userAccount = connectedUser.getAccount( );
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
