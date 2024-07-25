package com.openclassrooms.project.paymybuddy.service;

import com.openclassrooms.project.paymybuddy.config.MyUserDetails;
import com.openclassrooms.project.paymybuddy.repo.UserRepository;
import com.openclassrooms.project.paymybuddy.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername( String email ) throws BadCredentialsException
    {
        User user = userRepository.findByEmail( email );

        if( user == null )
        {
            throw new BadCredentialsException( "Could not find user" );
        }

        return new MyUserDetails( user );
    }
}
