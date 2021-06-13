package com.adtimokhin.repositories.user;

import com.adtimokhin.models.user.UserSurname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adtimokhin
 * 08.05.2021
 **/
@Repository
public interface UserSurnameRepository extends JpaRepository<UserSurname, Long> {

    UserSurname getById(long id);

    UserSurname getBySurname(String surname);

}
