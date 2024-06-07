package com.openclassrooms.project.paymybuddy.service;

import com.openclassrooms.project.paymybuddy.model.Connection;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.repo.ConnectionRepository;
import com.openclassrooms.project.paymybuddy.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.*;

@Service
@RequiredArgsConstructor
public class ConnectionService
{
    private final ConnectionRepository connectionRepository;

    private final UserRepository userRepository;


    public String addConnection( User connectedUser, User addedUser )
    {
        Connection connection = new Connection( );
        String message = null;

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

    public boolean connectionAlreadyExists( User connectedUser, User addedUser )
    {
        boolean connectionAlreadyExists = false;

        // get all pairs where connected user is listed
        List< Connection> existingConnectionsList = connectionRepository.getAllByConnectedUserOrAddedUser( connectedUser, connectedUser );

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

}
