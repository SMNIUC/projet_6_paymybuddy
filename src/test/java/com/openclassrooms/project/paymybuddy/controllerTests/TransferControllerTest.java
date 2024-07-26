package com.openclassrooms.project.paymybuddy.controllerTests;

import com.openclassrooms.project.paymybuddy.controllers.TransferController;
import com.openclassrooms.project.paymybuddy.model.Account;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.service.TransferService;
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
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TransferController.class)
@ExtendWith( MockitoExtension.class )
public class TransferControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @MockBean
    private TransferService transferService;

    @InjectMocks
    private TransferController transferControllerUnderTest;

    @BeforeEach
    public void setup( )
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( this.context )
                .apply( springSecurity( ) )
                .build( );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void doLoadTransferPageSuccessful( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );

        when( userService.findByEmail( anyString( ) ) ).thenReturn( connectedUser );
        when( transferService.getConnectionsUserList( connectedUser ) ).thenReturn( new ArrayList<>( ) );
        when( transferService.getTransferList( connectedUser ) ).thenReturn( new ArrayList<>( ) );

        // Act & Assert
        mockMvc.perform( get("/transfer" )
                        .principal( principal ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "user" ) )
                .andExpect( model( ).attribute( "user", connectedUser ) )
                .andExpect( model( ).attributeExists( "connections_list" ) )
                .andExpect( model( ).attribute( "connections_list", new ArrayList<>( ) ) )
                .andExpect( model( ).attributeExists( "transactions_list" ) )
                .andExpect( model( ).attribute( "transactions_list", new ArrayList<>( ) ) )
                .andExpect( view( ).name( "transfer" ) );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void doAddConnectionSuccessful( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        when( userService.findByEmail( "user" ) ).thenReturn( connectedUser );

        User addedUser = new User( );
        Account addedAccount = new Account( );
        addedUser.setAccount( addedAccount );
        addedUser.setEmail( "addedUser" );
        when( userService.findByEmail( "addedUser" ) ).thenReturn( addedUser );

        when( transferService.addConnection( connectedUser, addedUser ) ).thenReturn( "successfulConnection" );

        // Act & Assert
        mockMvc.perform( post("/addConnection" )
                        .param( "connectionEmail", addedUser.getEmail( ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "successfulConnection" ) )
                .andExpect( model( ).attribute( "successfulConnection", "successfulConnection" ) );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void errorAddingConnectionUnknownUser( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        when( userService.findByEmail( "user" ) ).thenReturn( connectedUser );

        User addedUser = new User( );
        Account addedAccount = new Account( );
        addedUser.setAccount( addedAccount );
        addedUser.setEmail( "addedUser" );
        when( userService.findByEmail( "addedUser" ) ).thenReturn( null );

        when( transferService.addConnection( connectedUser, addedUser ) ).thenReturn( "invalidUser" );

        // Act & Assert
        mockMvc.perform( post("/addConnection" )
                        .param( "connectionEmail", addedUser.getEmail( ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "invalidUser" ) )
                .andExpect( model( ).attribute( "invalidUser", "invalidUser" ) );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void errorAddingConnectionTransactionError( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        when( userService.findByEmail( "user" ) ).thenReturn( connectedUser );

        User addedUser = new User( );
        Account addedAccount = new Account( );
        addedUser.setAccount( addedAccount );
        addedUser.setEmail( "addedUser" );
        when( userService.findByEmail( "addedUser" ) ).thenReturn( addedUser );

        when( transferService.addConnection( connectedUser, addedUser ) ).thenReturn( "transactionError" );

        // Act & Assert
        mockMvc.perform( post("/addConnection" )
                        .param( "connectionEmail", addedUser.getEmail( ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "transactionError" ) )
                .andExpect( model( ).attribute( "transactionError", "transactionError" ) );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void errorAddingConnectionExistingUser( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        when( userService.findByEmail( "user" ) ).thenReturn( connectedUser );

        User addedUser = new User( );
        Account addedAccount = new Account( );
        addedUser.setAccount( addedAccount );
        addedUser.setEmail( "addedUser" );
        when( userService.findByEmail( "addedUser" ) ).thenReturn( addedUser );

        when( transferService.addConnection( connectedUser, addedUser ) ).thenReturn( "existingUser" );

        // Act & Assert
        mockMvc.perform( post("/addConnection" )
                        .param( "connectionEmail", addedUser.getEmail( ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "existingUser" ) )
                .andExpect( model( ).attribute( "existingUser", "existingUser" ) );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void doSendMoneyToConnectionSuccessful( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        when( userService.findByEmail( "user" ) ).thenReturn( connectedUser );

        User connectionUser = new User( );
        Account connectionAccount = new Account( );
        connectionUser.setAccount( connectionAccount );
        connectionUser.setEmail( "connectionUser" );
        when( userService.findByEmail( "connectionUser" ) ).thenReturn( connectionUser );

        double transactionAmount = 200;
        when( transferService.sendMoneyToConnection( connectedUser, connectionUser, transactionAmount ) ).thenReturn( "successfulTransaction" );

        // Act & Assert
        mockMvc.perform( post("/sendMoney" )
                        .param( "transactionConnectionEmail", connectionUser.getEmail( ) )
                        .param( "transactionConnectionAmount", String.valueOf( transactionAmount ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "successfulTransaction" ) )
                .andExpect( model( ).attribute( "successfulTransaction", "successfulTransaction" ) );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void errorSendingMoneyToConnectionMissingConnection( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        when( userService.findByEmail( "user" ) ).thenReturn( connectedUser );

        User connectionUser = new User( );
        Account connectionAccount = new Account( );
        connectionUser.setAccount( connectionAccount );
        connectionUser.setEmail( "connectionUser" );
        when( userService.findByEmail( "connectionUser" ) ).thenReturn( null );

        double transactionAmount = 200;
        when( transferService.sendMoneyToConnection( connectedUser, connectionUser, transactionAmount ) ).thenReturn( "missingConnection" );

        // Act & Assert
        mockMvc.perform( post("/sendMoney" )
                        .param( "transactionConnectionEmail", connectionUser.getEmail( ) )
                        .param( "transactionConnectionAmount", String.valueOf( transactionAmount ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "missingConnection" ) )
                .andExpect( model( ).attribute( "missingConnection", "missingConnection" ) );
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    void errorSendingMoneyToConnectioTransactionError( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        account.setAccountBalance( 50 );
        when( userService.findByEmail( "user" ) ).thenReturn( connectedUser );

        User connectionUser = new User( );
        Account connectionAccount = new Account( );
        connectionUser.setAccount( connectionAccount );
        connectionUser.setEmail( "connectionUser" );
        when( userService.findByEmail( "connectionUser" ) ).thenReturn( connectionUser );

        double transactionAmount = 200;
        when( transferService.sendMoneyToConnection( connectedUser, connectionUser, transactionAmount ) ).thenReturn( "transactionError" );

        // Act & Assert
        mockMvc.perform( post("/sendMoney" )
                        .param( "transactionConnectionEmail", connectionUser.getEmail( ) )
                        .param( "transactionConnectionAmount", String.valueOf( transactionAmount ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "transactionError" ) )
                .andExpect( model( ).attribute( "transactionError", "transactionError" ) );
    }
}
