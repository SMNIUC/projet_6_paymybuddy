package com.openclassrooms.project.paymybuddy.service;

import com.openclassrooms.project.paymybuddy.model.Account;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.openclassrooms.project.paymybuddy.utils.PayMyBuddyConstants.*;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Finds a user object in the database by their email address
     *
     * @param email
     * @return User object
     */
    public User findByEmail( String email )
    {
        return userRepository.findByEmail( email );
    }

    /**
     * Registers a new user object in the database
     *
     * @param email
     * @param password
     * @return validation/error message
     */
    @Transactional
    public String registerNewUser( String email, String password )
    {
        String message;

        if( userRepository.findByEmail( email ) == null )
        {
            User newUser = new User( );
            newUser.setEmail( email );
            newUser.setPassword( passwordEncoder.encode( password ) );
            newUser.setRole( ROLE_ADMIN );

            Account newAccount = accountService.createNewAccount( );
            newUser.setAccount( newAccount );

            userRepository.save( newUser );
            message = SUCCESSFUL_REGISTRATION;
        }
        else
        {
            message = EXISTING_EMAIL_ERROR;
        }

        return message;
    }
}
