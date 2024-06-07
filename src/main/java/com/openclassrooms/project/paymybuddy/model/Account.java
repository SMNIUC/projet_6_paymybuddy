package com.openclassrooms.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "account")
public class Account
{
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "balance")
    private int balance;

    @Column(name = "iban")
    private String iban;


}
