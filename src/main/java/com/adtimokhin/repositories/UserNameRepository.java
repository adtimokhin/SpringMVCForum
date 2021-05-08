package com.adtimokhin.repositories;

import com.adtimokhin.models.UserName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adtimokhin
 * 08.05.2021
 **/
@Repository
public interface UserNameRepository extends JpaRepository<UserName , Long> {

    UserName getById(long id);
}
