package com.openclassrooms.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "transfer")
public class Transfer
{
    @Id
    @Column(name = "transfer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transferId;

    @Column(name = "amount")
    private double transferAmount;

    @Column(name = "transfer_date")
    private Date transferDate;

    @OneToOne
    @JoinColumn(name = "sender_account_id", referencedColumnName = "account_id")
    private User transferSender;

    @OneToOne
    @JoinColumn(name = "recipient_account_id", referencedColumnName = "account_id")
    private User transferRecipient;

}
