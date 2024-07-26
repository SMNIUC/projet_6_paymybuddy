package com.openclassrooms.project.paymybuddy.controllerTests;

import com.openclassrooms.project.paymybuddy.controllers.PayMyBuddyController;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PayMyBuddyController.class)
@ExtendWith( MockitoExtension.class )
public class PayMyBuddyControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @InjectMocks
    private PayMyBuddyController payMyBuddyControllerUnderTest;

    private Principal principal;
    private User connectedUser;

    @BeforeEach
    public void setup( )
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( this.context )
                .apply( springSecurity( ) )
                .build( );

        principal = ( ) -> "user"; // Mock Principal object

        connectedUser = new User( );
        Account connectedAccount = new Account( );
        connectedUser.setAccount( connectedAccount );
        connectedUser.setEmail( "user" );

        when( userService.findByEmail( "user" ) ).thenReturn( connectedUser );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void doLogInSuccessful( ) throws Exception
    {
        // Arrange

        // Act & Assert
        mockMvc.perform( get("/login" )
                        .param( "error", String.valueOf( false ) )
                        .principal( principal ) )
                .andExpect( status( ).isOk( ) );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void errorLogginIn( ) throws Exception
    {
        // Arrange

        // Act & Assert
        mockMvc.perform( get("/login" )
                        .param( "error", String.valueOf( true ) )
                        .principal( principal ) )
                .andExpect( status( ).isOk( ) );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void doLoadPayMyBuddyHomepage( ) throws Exception
    {
        // Arrange

        // Act & Assert
        mockMvc.perform( get("/home" )
                        .principal( principal ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "connectedUser" ) )
                .andExpect( model( ).attribute( "connectedUser", connectedUser ) )
                .andExpect( view( ).name( "index" ) );
    }

    @Test
    @WithMockUser( username = "user", roles = {"admin"} )
    void doCreateNewAccountSuccessful( ) throws Exception
    {
        // Arrange
        connectedUser.setPassword( "password" );
        when( userService.registerNewUser( connectedUser.getEmail( ), connectedUser.getPassword( ) ) ).thenReturn( "successfulRegistration" );

        // Act & Assert
        mockMvc.perform( post("/registration" )
                        .param( "username", connectedUser.getEmail( ) )
                        .param( "password", connectedUser.getPassword( ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "successfulRegistration" ) )
                .andExpect( model( ).attribute( "successfulRegistration", "successfulRegistration" ) );
    }

    @Test
    @WithMockUser( username = "user", roles = {"admin"} )
    void errorCreatingNewAccountExistingEmail( ) throws Exception
    {
        // Arrange
        connectedUser.setPassword( "password" );
        when( userService.registerNewUser( connectedUser.getEmail( ), connectedUser.getPassword( ) ) ).thenReturn( "existingEmail" );

        // Act & Assert
        mockMvc.perform( post("/registration" )
                        .param( "username", connectedUser.getEmail( ) )
                        .param( "password", connectedUser.getPassword( ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "existingEmail" ) )
                .andExpect( model( ).attribute( "existingEmail", "existingEmail" ) );
    }
}
