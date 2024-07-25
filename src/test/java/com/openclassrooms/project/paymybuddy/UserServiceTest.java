package com.openclassrooms.project.paymybuddy;

import com.openclassrooms.project.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;

@SpringBootTest
public class UserServiceTest
{
     private UserService userServiceUnderTest;

     @BeforeEach
     void initUserService( )
     {
          userServiceUnderTest = new UserService( UserRepository userRepository, AccountService accountService, BCryptPasswordEncoder passwordEncoder );
     }

     @Test
     void newUserGetsRegistered( )
     {
          // Given
          String email = "john.doe@gmail.com";
          String password = "password";

          // When
          String newUserRegisteredMessage = userServiceUnderTest.registerNewUser( email, password );

          // Then
          assertThat()

     }

}
