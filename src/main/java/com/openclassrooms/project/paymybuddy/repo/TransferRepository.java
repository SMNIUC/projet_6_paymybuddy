package com.openclassrooms.project.paymybuddy.repo;

import com.openclassrooms.project.paymybuddy.model.Transfer;
import com.openclassrooms.project.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Integer>
{
    List<Transfer> getAllByTransferRecipient( User transferRecipient );
}
