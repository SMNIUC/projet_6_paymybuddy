package com.openclassrooms.project.paymybuddy.repo;

import com.openclassrooms.project.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>
{
    User findByEmail( String email );

    boolean existsByEmail( String email );

}
