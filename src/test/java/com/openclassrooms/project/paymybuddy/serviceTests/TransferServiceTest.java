package com.openclassrooms.project.paymybuddy.serviceTests;

import com.openclassrooms.project.paymybuddy.model.Account;
import com.openclassrooms.project.paymybuddy.model.Connection;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.repo.AccountRepository;
import com.openclassrooms.project.paymybuddy.repo.ConnectionRepository;
import com.openclassrooms.project.paymybuddy.repo.TransferRepository;
import com.openclassrooms.project.paymybuddy.repo.UserRepository;
import com.openclassrooms.project.paymybuddy.service.TransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class TransferServiceTest
{
    @Mock
    private ConnectionRepository connectionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferService transferServiceUnderTest;

    @Test
    void doAddConnectionSuccessful( )
    {
        // Given
        User connnectedUser = new User( );
        User addedUser = new User( );
        String message = "successfulConnection";
        when( userRepository.existsByEmail( addedUser.getEmail( ) ) ).thenReturn( true );

        // When
        String confirmationMessage = transferServiceUnderTest.addConnection( connnectedUser, addedUser );

        // Then
        assertThat( confirmationMessage ).isEqualTo( message );
    }

    @Test
    void errorAddingConnectionUserUnknown( )
    {
        // Given
        User connnectedUser = new User( );
        User addedUser = new User( );
        String message = "invalidUser";
        when( userRepository.existsByEmail( addedUser.getEmail( ) ) ).thenReturn( false );

        // When
        String confirmationMessage = transferServiceUnderTest.addConnection( connnectedUser, addedUser );

        // Then
        assertThat( confirmationMessage ).isEqualTo( message );
    }

    @Test
    void errorAddingConnectionAlreadyExists( )
    {
        // Given
        User connnectedUser = new User( );
        User addedUser = new User( );
        String message = "existingUser";
        Connection connection = new Connection( );
        connection.setAddedUser( addedUser );
        connection.setConnectedUser( connnectedUser );
        when( connectionRepository.getAllByConnectedUserOrAddedUser( connnectedUser, connnectedUser ) ).thenReturn( List.of( connection ) );

        // When
        String confirmationMessage = transferServiceUnderTest.addConnection( connnectedUser, addedUser );

        // Then
        assertThat( confirmationMessage ).isEqualTo( message );
    }

    @Test
    void getConnectionsUserListSuccessful( )
    {
        // Given
        User connnectedUser = new User( );
        User addedUser = new User( );
        Connection connection = new Connection( );
        connection.setAddedUser( addedUser );
        connection.setConnectedUser( connnectedUser );
        when( connectionRepository.getAllByConnectedUserOrAddedUser( connnectedUser, connnectedUser ) ).thenReturn( List.of( connection ) );

        // When
        List<User> connectionsUserList = transferServiceUnderTest.getConnectionsUserList( connnectedUser );

        // Then
        assertThat( connectionsUserList ).isNotNull( );
        assertThat( connectionsUserList.size( ) ).isEqualTo( 1 );
        assertThat( connectionsUserList.get( 0 ) ).isEqualTo( addedUser );
    }

    @Test
    void doSendMoneyToConnectionSuccessful( )
    {
        // Given
        User sender = new User( );
        Account senderAccount = new Account( );
        sender.setAccount( senderAccount );
        senderAccount.setAccountBalance( 200 );

        User recipient = new User( );
        Account recipientAccount = new Account( );
        recipient.setAccount( recipientAccount );
        recipientAccount.setAccountBalance( 300 );

        double transferAmount = 100;
        double transactionFee = 100 * 0.005;
        String message = "successfulTransaction";

        // When
        String confirmationMessage = transferServiceUnderTest.sendMoneyToConnection( sender, recipient, transferAmount );

        // Then
        assertThat( confirmationMessage ).isEqualTo( message );
        assertThat( sender.getAccount( ).getAccountBalance( ) ).isEqualTo( 200 - transferAmount - transactionFee );
        assertThat( recipientAccount.getAccountBalance( ) ).isEqualTo( 300 + transferAmount );
    }

    @Test
    void errorSendingMoneyToConnection( )
    {
        // Given
        User sender = new User( );
        Account senderAccount = new Account( );
        sender.setAccount( senderAccount );
        senderAccount.setAccountBalance( 50 );

        User recipient = new User( );
        Account recipientAccount = new Account( );
        recipient.setAccount( recipientAccount );

        double transferAmount = 100;
        String message = "transactionError";

        // When
        String errorMessage = transferServiceUnderTest.sendMoneyToConnection( sender, recipient, transferAmount );

        // Then
        assertThat( errorMessage ).isEqualTo( message );
    }
}
