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
    private int transferAmount;

    @Column(name = "transfer_date")
    private Date transferDate;

    @Column(name = "sender_account_id")
    private int senderAccountId;

    @Column(name = "recipient_account_id")
    private int recipientAccountId;

    private int transcationCost;

    private int calculateTransactionCost()
    {
        return transcationCost;
    }
}
