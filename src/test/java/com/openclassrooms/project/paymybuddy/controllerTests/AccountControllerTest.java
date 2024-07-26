package com.openclassrooms.project.paymybuddy.controllerTests;

import com.openclassrooms.project.paymybuddy.controllers.AccountController;
import com.openclassrooms.project.paymybuddy.model.Account;
import com.openclassrooms.project.paymybuddy.model.User;
import com.openclassrooms.project.paymybuddy.service.AccountService;
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

@WebMvcTest(controllers = AccountController.class)
@ExtendWith( MockitoExtension.class )
public class AccountControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService;

    @MockBean
    private TransferService transferService;

    @InjectMocks
    private AccountController accountControllerUnderTest;

    @BeforeEach
    public void setup( )
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( this.context )
                .apply( springSecurity( ) )
                .build( );
    }

    @Test
    @WithMockUser( username = "user", roles = {"admin"} )
    void doLoadProfilePageSuccessful( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );

        when( userService.findByEmail( anyString( ) ) ).thenReturn( connectedUser );
        when( transferService.getConnectionsUserList( connectedUser ) ).thenReturn( new ArrayList<>( ) );

        // Act & Assert
        mockMvc.perform( get("/profile" )
                        .principal( principal ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "user" ) )
                .andExpect( model( ).attribute( "user", connectedUser ) )
                .andExpect( model( ).attributeExists( "connections_list" ) )
                .andExpect( model( ).attribute( "connections_list", new ArrayList<>( ) ) )
                .andExpect( view( ).name( "profile" ) );
    }

    @Test
    @WithMockUser( username = "user", roles = {"admin"} )
    void doTransferMoneyBetweenAccountsSuccessful( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        account.setIban( "US15JDIM25968475125699" );
        when( userService.findByEmail( anyString( ) ) ).thenReturn( connectedUser );

        String bankTransferAction = "bankToAccount";
        double bankTransferAmount = 200;
        when( accountService.doTransferBankToAccount( connectedUser, bankTransferAmount ) ).thenReturn( "successfulTransfer" );

        // Act & Assert
        mockMvc.perform( post("/bankAccountTransfer" )
                    .param( "bankTransferAction", bankTransferAction )
                    .param( "bankTransferAmount", String.valueOf( bankTransferAmount ) )
                    .principal( principal )
                    .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "successfulTransfer" ) )
                .andExpect( model( ).attribute( "successfulTransfer", "successfulTransfer" ) );
    }

    @Test
    @WithMockUser( username = "user", roles = {"admin"} )
    void errorTransferringMoneyBankRefusal( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        account.setIban( "US15JDIM25968475125699" );
        when( userService.findByEmail( anyString( ) ) ).thenReturn( connectedUser );

        String bankTransferAction = "bankToAccount";
        double bankTransferAmount = 200;
        when( accountService.doTransferBankToAccount( connectedUser, bankTransferAmount ) ).thenReturn( "bankRefusal" );

        // Act & Assert
        mockMvc.perform( post("/bankAccountTransfer" )
                        .param( "bankTransferAction", bankTransferAction )
                        .param( "bankTransferAmount", String.valueOf( bankTransferAmount ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "bankRefusal" ) )
                .andExpect( model( ).attribute( "bankRefusal", "bankRefusal" ) );
    }

    @Test
    @WithMockUser( username = "user", roles = {"admin"} )
    void errorTransferringMoneyBalanceTooLow( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        account.setIban( "US15JDIM25968475125699" );
        when( userService.findByEmail( anyString( ) ) ).thenReturn( connectedUser );

        String bankTransferAction = "accountToBank";
        double bankTransferAmount = 200;
        when( accountService.doTransferAccountToBank( connectedUser, bankTransferAmount ) ).thenReturn( "balanceTooLow" );

        // Act & Assert
        mockMvc.perform( post("/bankAccountTransfer" )
                        .param( "bankTransferAction", bankTransferAction )
                        .param( "bankTransferAmount", String.valueOf( bankTransferAmount ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "balanceTooLow" ) )
                .andExpect( model( ).attribute( "balanceTooLow", "balanceTooLow" ) );
    }

    @Test
    @WithMockUser( username = "user", roles = {"admin"} )
    void errorTransferringMoneyNoActionSelected( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        account.setIban( "US15JDIM25968475125699" );
        when( userService.findByEmail( anyString( ) ) ).thenReturn( connectedUser );

        // No action selected by the user
        String bankTransferAction = "null";
        double bankTransferAmount = 200;

        // Act & Assert
        mockMvc.perform( post("/bankAccountTransfer" )
                        .param( "bankTransferAction", bankTransferAction )
                        .param( "bankTransferAmount", String.valueOf( bankTransferAmount ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "selectAnAction" ) )
                .andExpect( model( ).attribute( "selectAnAction", "selectAnAction" ) );
    }

    @Test
    @WithMockUser( username = "user", roles = {"admin"} )
    void errorTransferringMoneyIbanNull( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        account.setIban( "" );
        when( userService.findByEmail( anyString( ) ) ).thenReturn( connectedUser );

        String bankTransferAction = "accountToBank";
        double bankTransferAmount = 200;

        // Act & Assert
        mockMvc.perform( post("/bankAccountTransfer" )
                        .param( "bankTransferAction", bankTransferAction )
                        .param( "bankTransferAmount", String.valueOf( bankTransferAmount ) )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "nullIbanTransferError" ) )
                .andExpect( model( ).attribute( "nullIbanTransferError", "nullIbanTransferError" ) );
    }

    @Test
    @WithMockUser( username = "user", roles = {"admin"} )
    void doIbanRegistrationSuccessful( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        account.setIban( "" );
        when( userService.findByEmail( anyString( ) ) ).thenReturn( connectedUser );

        String addedIban = "US15JDIM25968475125699";
        when( accountService.registerUserIban( addedIban, connectedUser ) ).thenReturn( "ibanSuccess" );

        // Act & Assert
        mockMvc.perform( post("/ibanRegistration" )
                        .param( "iban", addedIban )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "ibanSuccess" ) )
                .andExpect( model( ).attribute( "ibanSuccess", "ibanSuccess" ) );
    }

    @Test
    @WithMockUser( username = "user", roles = {"admin"} )
    void errorRegisteringIbanIbanIsNull( ) throws Exception
    {
        // Arrange
        Principal principal = ( ) -> "user"; // Mock Principal object
        User connectedUser = new User( );
        Account account = new Account( );
        connectedUser.setAccount( account );
        connectedUser.setEmail( "user" );
        account.setIban( "" );
        when( userService.findByEmail( anyString( ) ) ).thenReturn( connectedUser );

        String addedIban = "US15JDIM25968475125699";
        when( accountService.registerUserIban( addedIban, connectedUser ) ).thenReturn( "nullIbanError" );

        // Act & Assert
        mockMvc.perform( post("/ibanRegistration" )
                        .param( "iban", addedIban )
                        .principal( principal )
                        .with( csrf( ) ) )
                .andExpect( status( ).isOk( ) )
                .andExpect( model( ).attributeExists( "nullIbanError" ) )
                .andExpect( model( ).attribute( "nullIbanError", "nullIbanError" ) );
    }
}
