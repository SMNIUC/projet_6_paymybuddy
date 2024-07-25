package com.openclassrooms.project.paymybuddy.service;

import com.openclassrooms.project.paymybuddy.model.Connection;
import com.openclassrooms.project.paymybuddy.model.Transfer;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.repo.AccountRepository;
import com.openclassrooms.project.paymybuddy.repo.ConnectionRepository;
import com.openclassrooms.project.paymybuddy.repo.TransferRepository;
import com.openclassrooms.project.paymybuddy.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.*;


@Service
@RequiredArgsConstructor
public class TransferService
{
    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;


    public String addConnection( User connectedUser, User addedUser )
    {
        Connection connection = new Connection( );
        String message;

        if( !connectionAlreadyExists( connectedUser, addedUser ) )
        {
            if( userRepository.existsByEmail( addedUser.getEmail( ) ) )
            {
                connection.setConnectedUser( connectedUser );
                connection.setAddedUser( addedUser );
                connectionRepository.save( connection );
                message = SUCCESSFUL_CONNECTION;
            }
            else
            {
                message = UNKNOWN_USER_ERROR;
            }
        }
        else
        {
            message = EXISTING_USER_ERROR;
        }
        return message;
    }


    private boolean connectionAlreadyExists( User connectedUser, User addedUser )
    {
        boolean connectionAlreadyExists = false;

        // get all pairs where connected user is listed
        List<Connection> existingConnectionsList = connectionRepository.getAllByConnectedUserOrAddedUser( connectedUser, connectedUser );

        // check for the list of existing connections whether the other user matches our addedUser
        for( Connection existingConnection : existingConnectionsList )
        {
            if ( existingConnection.getConnectedUser( ) == addedUser || existingConnection.getAddedUser( ) == addedUser )
            {
                connectionAlreadyExists = true;
                break;
            }
        }
        return connectionAlreadyExists;
    }


    public List<User> getConnectionsUserList( User connectedUser )
    {
        List<User> connectionsUserList = new ArrayList<>( );
        List<Connection> existingConnectionsList = connectionRepository.getAllByConnectedUserOrAddedUser( connectedUser, connectedUser );

        for( Connection connection : existingConnectionsList )
        {
            if( connection.getConnectedUser( ) == connectedUser )
            {
                connectionsUserList.add( connection.getAddedUser( ) );
            }
            if( connection.getAddedUser( ) == connectedUser )
            {
                connectionsUserList.add( connection.getConnectedUser( ) );
            }
        }

        return connectionsUserList;
    }

    public List<Transfer> getTransferList( User connectedUser )
    {
        return transferRepository.getAllByTransferRecipient( connectedUser );
    }

    private void saveNewTransfer( User connectedUser, User connectionUser, double transactionAmount )
    {
        Transfer newTransfer = new Transfer( );

        newTransfer.setTransferAmount( transactionAmount );
        newTransfer.setTransferDate( new Date( ) );
        newTransfer.setTransferRecipient( connectionUser );
        newTransfer.setTransferSender( connectedUser );

        transferRepository.save( newTransfer );
    }

    @Transactional
    public String sendMoneyToConnection( User connectedUser, User connectionUser, double transactionAmount )
    {

        double connectedUserAccountBalance = connectedUser.getAccount( ).getAccountBalance( );
        double connectionUserAccountBalance = connectionUser.getAccount( ).getAccountBalance( );
        double transactionFee = transactionAmount * 0.005;
        String message;

        // if connected user has sufficient balance, sends the money to their connection, including the 0.5% fee (fee's at sender's charge)
        if( connectedUserAccountBalance >= transactionAmount )
        {
            // Taking the transferred amount + the transaction fee from the connected user account and putting transferred amount into the connection's account
            connectedUserAccountBalance -= ( transactionAmount - transactionFee );
            connectionUserAccountBalance += transactionAmount;

            // Register the new transfer in database
            saveNewTransfer( connectedUser, connectionUser, transactionAmount );

            // Setting the new balance in each account
            connectedUser.getAccount( ).setAccountBalance( connectedUserAccountBalance );
            connectionUser.getAccount( ).setAccountBalance( connectionUserAccountBalance );

            // Saving the updated accounts into persistence
            accountRepository.save( connectedUser.getAccount( ) );
            accountRepository.save( connectionUser.getAccount( ) );

            message = SUCCESSFUL_TRANSACTION;
        }
        else
        {
            message = TRANSACTION_ERROR;
            //TODO -> exception
            //throw new Exception(  );
        }

        return message;
    }
}
