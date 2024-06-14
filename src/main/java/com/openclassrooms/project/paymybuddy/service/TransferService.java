package com.openclassrooms.project.paymybuddy.service;

import com.openclassrooms.project.paymybuddy.model.Connection;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.repo.AccountRepository;
import com.openclassrooms.project.paymybuddy.repo.ConnectionRepository;
import com.openclassrooms.project.paymybuddy.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.*;


@Service
@RequiredArgsConstructor
public class TransferService
{
    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;


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
        List<Connection> existingConnectionsList = connectionRepository.getAllAddedUserByConnectedUser( connectedUser );

        for( Connection connection : existingConnectionsList )
        {
            connectionsUserList.add( connection.getAddedUser( ) );
        }

        return connectionsUserList;
    }


    @Transactional
    public String sendMoneyToConnection( User connectedUser, User connectionUser, double transactionAmount )
    {
        double connectedUserAccountBalance = connectedUser.getAccountId( ).getAccountBalance( );
        double connectionUserAccountBalance = connectionUser.getAccountId( ).getAccountBalance( );
        double transactionFee = transactionAmount * 0.005;
        String message;

        // if connected user has sufficient balance, send the money to their connection, including the 0.5% fee
        if( connectedUserAccountBalance >= transactionAmount )
        {
            // Taking the transferred amount + the transaction fee from the connected user account and putting transferred amount into the connection's account
            connectedUserAccountBalance -= ( transactionAmount - transactionFee );
            connectionUserAccountBalance += transactionAmount;

            // Setting the new balances in each account
            connectedUser.getAccountId( ).setAccountBalance( connectedUserAccountBalance );
            connectionUser.getAccountId( ).setAccountBalance( connectionUserAccountBalance );

            // Saving the updated accounts into persistence
            accountRepository.save( connectedUser.getAccountId( ) );
            accountRepository.save( connectionUser.getAccountId( ) );

            message = SUCCESSFUL_TRANSACTION;
        }
        else
        {
            message = TRANSACTION_ERROR;
            //throw new Exception(  );
        }

        return message;
    }
}
