package com.openclassrooms.project.paymybuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Transfer
{
    private int transferId;
    private int transferAmount;
    private Date transferDate;
    private int transcationCost;

    private int calculateTransactionCost()
    {
        return transcationCost;
    }
}
