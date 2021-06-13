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

    @Transactional
    void addUser(User user,boolean generateRandomName, Role... roles);

    @Transactional
    void addOrganizationMember(User user, String tokenValue, String firstName, String lastName);

    void assignUserFullName(User user);

    @Transactional
    void deleteUser(User user);

    List<User> getAllUsers();

    List<User> getAllReportedUsers();

    List<User> getAllBannedUsers();

    void banUser(User user);

    void unBanUser(User user);

    boolean isFirstTime(User user);

    void setUserEnteredTheForum(User user);

    List<String> getAllEmailVerificationTokens();

    boolean verifyEmail(String token);
}
