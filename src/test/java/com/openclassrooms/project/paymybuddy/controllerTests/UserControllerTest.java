package com.openclassrooms.project.paymybuddy.controllerTests;

import com.openclassrooms.project.paymybuddy.controllers.UserController;
import com.openclassrooms.project.paymybuddy.model.Account;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@ExtendWith( MockitoExtension.class )
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController UserControllerUnderTest;

    @BeforeEach
    public void setup( )
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( this.context )
                .apply( springSecurity( ) )
                .build( );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void doLoadContactPageSuccessful( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );

        when( userService.findByEmail( anyString( ) ) ).thenReturn( connectedUser );

        // Act & Assert
        mockMvc.perform( get("/contact" )
                        .principal( principal ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "connectedUser" ) )
                .andExpect( model( ).attribute( "connectedUser", connectedUser ) )
                .andExpect( view( ).name( "contact" ) );
    }
}
