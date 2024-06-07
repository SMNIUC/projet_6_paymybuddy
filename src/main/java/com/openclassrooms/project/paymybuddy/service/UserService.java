package com.openclassrooms.project.paymybuddy.service;

import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.repo.ConnectionRepository;
import com.openclassrooms.project.paymybuddy.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserRepository userRepository;

    private final ConnectionRepository connectionRepository;

    public User findByEmail( String email )
    {
        return userRepository.findByEmail( email );
    }

    public List<User> getAllUsers()
    {
        return ( List< User > ) userRepository.findAll();
    }


//    public List<String> getAllUsersList( )
//    {
//        List<User> allUser = getAllUsers();
//
//        List<String> allUserEmails
//        return
//    }
}
