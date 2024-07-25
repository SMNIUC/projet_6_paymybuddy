package com.openclassrooms.project.paymybuddy;

import com.openclassrooms.project.paymybuddy.model.Account;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.repo.AccountRepository;
import com.openclassrooms.project.paymybuddy.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith( MockitoExtension.class )
public class AccountServiceTest
{
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountServiceUnderTest;

    @Test
    void doCreateNewAccount( )
    {
        // Given

        // When
        Account account = accountServiceUnderTest.createNewAccount( );

        // Then
        assertThat( account ).isNotNull( );
    }

    //TODO -> how to test @Transactional errors?
//    @Test
//    void errorCreatingNewAccount( )
//    {
//        // Given
//
//        // When
//
//        // Then
//
//    }

    @Test
    void doRegisterUserIban( )
    {
        // Given
        String iban = "US12PDJT65841516847685";
        User user = new User( );
        Account account = new Account( );
        user.setAccount( account );

        // When
        accountServiceUnderTest.registerUserIban( iban, user );

        // Then
        assertThat( user.getAccount( ).getIban( ) ).isEqualTo( iban );
    }

    @Test
    void errorRegisteringUserIbanNull( )
    {
        // Given
        String message = "nullIbanError";
        String iban = "";
        User user = new User( );
        Account account = new Account( );
        user.setAccount( account );

        // When
        String errorMessage = accountServiceUnderTest.registerUserIban( iban, user );

        // Then
        assertThat( errorMessage ).isEqualTo( message );
    }

    @Test
    void doTransferBankToAccount( )
    {
        // Given
        User user = new User( );
        Account account = new Account( );
        user.setAccount( account );
        account.setAccountBalance( 200 );
        account.setBankBalance( 300 );
        double transferAmount = 100;
        String message = "successfulTransfer";

        // When
        String resultMessage = accountServiceUnderTest.doTransferBankToAccount( user, transferAmount );

        // Then
        assertThat( resultMessage ).isEqualTo( message );
        assertThat( account.getAccountBalance( ) ).isEqualTo( 200 + transferAmount );
        assertThat( account.getBankBalance( ) ).isEqualTo( 300 - transferAmount );
    }

    @Test
    void errorTransferringBankToAccountPaymentRefused( )
    {
        // Given
        User user = new User( );
        Account account = new Account( );
        user.setAccount( account );
        account.setBankBalance( 50 );
        double transferAmount = 100;
        String message = "bankRefusal";

        // When
        String resultMessage = accountServiceUnderTest.doTransferBankToAccount( user, transferAmount );

        // Then
        assertThat( resultMessage ).isEqualTo( message );
    }

    @Test
    void doTransferAccountToBank( )
    {
        // Given
        User user = new User( );
        Account account = new Account( );
        user.setAccount( account );
        account.setAccountBalance( 200 );
        account.setBankBalance( 300 );
        double transferAmount = 100;
        String message = "successfulTransfer";

        // When
        String resultMessage = accountServiceUnderTest.doTransferAccountToBank( user, transferAmount );

        // Then
        assertThat( resultMessage ).isEqualTo( message );
        assertThat( account.getAccountBalance( ) ).isEqualTo( 200 - transferAmount );
        assertThat( account.getBankBalance( ) ).isEqualTo( 300 + transferAmount );
    }

    @Test
    void errorTransferringAccountToBankBalanceTooLow( )
    {
        // Given
        User user = new User( );
        Account account = new Account( );
        user.setAccount( account );
        account.setAccountBalance( 50 );
        double transferAmount = 100;
        String message = "balanceTooLow";

        // When
        String resultMessage = accountServiceUnderTest.doTransferAccountToBank( user, transferAmount );

        // Then
        assertThat( resultMessage ).isEqualTo( message );
    }

}
