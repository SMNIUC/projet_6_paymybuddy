package com.openclassrooms.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User
{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "birthday")
    private Date birthday;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account accountId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

}
