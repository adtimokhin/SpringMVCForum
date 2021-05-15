package com.adtimokhin.services.user;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Service
public interface UserService {

    User getUser(String email);

    User getUser(long id);

    void addUser(User user, Role... roles);

    void assignUserFullName(User user);

    @Transactional
    void deleteUser(User user);

    List<User> getAllUsers();

    List<User> getAllReportedUsers();

    List<User> getAllBannedUsers();
}
