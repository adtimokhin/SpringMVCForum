package com.adtimokhin.repositories.user;

import com.adtimokhin.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);

    List<User> getAllByBanned(boolean banned);

    User findById(long id);

    List<User> findAllByEmailVerificationTokenIsNotNull();

    List<User> findAllByPasswordRestoreTokenIsNotNull();

    User findByEmailVerificationToken(String token);

    User findByPasswordRestoreToken(String token);
}


// Американский Вандал