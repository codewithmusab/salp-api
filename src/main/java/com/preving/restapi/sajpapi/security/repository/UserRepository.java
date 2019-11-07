package com.preving.restapi.sajpapi.security.repository;


import com.preving.restapi.sajpapi.model.domains.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User findUserById(long id);

}
