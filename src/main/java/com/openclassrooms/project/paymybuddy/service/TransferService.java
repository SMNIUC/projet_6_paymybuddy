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

    /**
     * Creates and saves in the database a new connection
     * between the connected user and another user of the
     * application
     *
     * @param connectedUser
     * @param addedUser
     * @return validation or error message
     */
    @Transactional
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

    /**
     * Checks whether a connection already exists in the database
     * between the connected user and another user
     *
     * @param connectedUser
     * @param addedUser
     * @return true if connection exists, false is not
     */
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

    /**
     * Gets the list of all the connections of the connected user
     *
     * @param connectedUser
     * @return list of users
     */
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

    /**
     * Gets the list of all transfers ever received by the connected user
     *
     * @param connectedUser
     * @return list of transfers
     */
    public List<Transfer> getTransferList( User connectedUser )
    {
        return transferRepository.getAllByTransferRecipient( connectedUser );
    }

    /**
     * Saves a new transfer between users in the database
     *
     * @param connectedUser
     * @param connectionUser
     * @param transactionAmount
     */
    @Transactional
    protected void saveNewTransfer( User connectedUser, User connectionUser, double transactionAmount )
    {
        Transfer newTransfer = new Transfer( );

        newTransfer.setTransferAmount( transactionAmount );
        newTransfer.setTransferDate( new Date( ) );
        newTransfer.setTransferRecipient( connectionUser );
        newTransfer.setTransferSender( connectedUser );

        transferRepository.save( newTransfer );
    }

    /**
     * Sends a specified amount from the connected user account
     * to the account of another specified user.
     * The 0.5% transaction fee is at the charge of the sender
     *
     * @param sender
     * @param recipient
     * @param transactionAmount
     * @return validation or error message
     */
    @Transactional
    public String sendMoneyToConnection( User sender, User recipient, double transactionAmount )
    {

        double senderAccountBalance = sender.getAccount( ).getAccountBalance( );
        double recipientAccountBalance = recipient.getAccount( ).getAccountBalance( );
        double transactionFee = transactionAmount * 0.005;
        String message;

        // if sender has sufficient balance, sends the money to their connection/recipient, including the 0.5% fee (fee's at sender's charge)
        if( senderAccountBalance >= transactionAmount )
        {
            // Taking the transferred amount + the transaction fee from the sender's account and putting transferred amount into the recipient's account
            senderAccountBalance -= ( transactionAmount + transactionFee );
            recipientAccountBalance += transactionAmount;

            // Register the new transfer in database
            saveNewTransfer( sender, recipient, transactionAmount );

            // Setting the new balance in each account
            sender.getAccount( ).setAccountBalance( senderAccountBalance );
            recipient.getAccount( ).setAccountBalance( recipientAccountBalance );

            // Saving the updated accounts into persistence
            accountRepository.save( sender.getAccount( ) );
            accountRepository.save( recipient.getAccount( ) );

            message = SUCCESSFUL_TRANSACTION;
        }
        else
        {
            message = TRANSACTION_ERROR;
        }

        return message;
    }
}
