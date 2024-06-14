package com.openclassrooms.project.paymybuddy.repo;

import com.openclassrooms.project.paymybuddy.model.Connection;
import com.openclassrooms.project.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, Integer>
{
    List<Connection> getAllByConnectedUserOrAddedUser( User connectedUser, User addedUser );

    List<Connection> getAllAddedUserByConnectedUser( User connectedUser );
}
