package com.openclassrooms.project.paymybuddy;

import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.repo.UserRepository;
import com.openclassrooms.project.paymybuddy.service.AccountService;
import com.openclassrooms.project.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class UserServiceTest
{
     @Mock
     private UserRepository userRepository;

     @Mock
     private AccountService accountService;

     @Mock
     private BCryptPasswordEncoder passwordEncoder;

     @InjectMocks
     private UserService userServiceUnderTest;

     @Test
     void doRegisterNewUser( )
     {
          // Given
          String email = "john.doe@gmail.com";
          String password = "password";
          String message = "successfulRegistration";
          String encodedPassword = "encodedPassword";
          when( passwordEncoder.encode( anyString( ) ) ).thenReturn( encodedPassword );

          // When
          String newUserRegisteredMessage = userServiceUnderTest.registerNewUser( email, password );

          // Then
          assertThat( newUserRegisteredMessage ).isEqualTo( message );
     }

     @Test
     void errorRegisteringNewUser( )
     {
          // Given
          String email = "john.doe@gmail.com";
          String password = "password";
          String message = "existingEmail";
          when( userRepository.findByEmail( email ) ).thenReturn( new User( ) );

          // When
          String newUserRegisteredMessage = userServiceUnderTest.registerNewUser( email, password );

          // Then
          assertThat( newUserRegisteredMessage ).isEqualTo( message );
     }

}
