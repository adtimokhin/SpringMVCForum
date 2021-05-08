package com.adtimokhin.services;

import com.adtimokhin.models.Roles;
import com.adtimokhin.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Service
public interface UserService {

    User getUser(String email);

    void addUser(User user, Roles... roles);

    void assignUserFullName(User user);

    @Transactional
    void deleteUser(User user);
}
