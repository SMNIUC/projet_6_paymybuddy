package com.openclassrooms.project.paymybuddy;

import com.openclassrooms.project.paymybuddy.controllers.PayMyBuddyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymybuddyApplicationTests {

    @Autowired
    private PayMyBuddyController controller;

    // Tests that the Application is starting and that the context does indeed create my controller
    @Test
    void contextLoads() throws Exception
    {
        assertThat(controller).isNotNull();
    }

}
