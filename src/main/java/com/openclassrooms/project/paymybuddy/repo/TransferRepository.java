package com.openclassrooms.project.paymybuddy.repo;

import com.openclassrooms.project.paymybuddy.model.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Integer>
{
}
