package com.adtimokhin.repositories.user;

import com.adtimokhin.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
}
//public interface UserRepository extends JpaRepository<User, Long> {
//
//    User findByEmail(String email);
//}
