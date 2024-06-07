package com.openclassrooms.project.paymybuddy.repo;

import com.openclassrooms.project.paymybuddy.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>
{

}
