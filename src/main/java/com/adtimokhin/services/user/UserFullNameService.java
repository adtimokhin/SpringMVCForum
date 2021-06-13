package com.adtimokhin.services.user;

import com.adtimokhin.models.user.UserName;
import com.adtimokhin.models.user.UserSurname;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adtimokhin
 * 13.06.2021
 **/

@Service
public interface UserFullNameService {

    UserName getUserName(long id);
    UserName getUserName(String name);

    UserSurname getUserSurname(long id);
    UserSurname getUserSurname(String surname);

    String formatNameOrSurname(String name);

    @Transactional
    void addUserName(String name);
    @Transactional
    void addUserSurname(String surname);

    long getNumberOfUserNames();
    long getNumberOfUserSurnames();



}
