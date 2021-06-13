package com.adtimokhin.repositories.user;

import com.adtimokhin.models.user.UserName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adtimokhin
 * 08.05.2021
 **/
@Repository
public interface UserNameRepository extends JpaRepository<UserName , Long> {

    UserName getById(long id);

    UserName getByName(String name);
}
