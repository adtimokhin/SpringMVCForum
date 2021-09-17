package com.adtimokhin.services.user;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.user.User;
import com.adtimokhin.models.user.UserName;
import com.adtimokhin.models.user.UserSurname;
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

    UserName getRandomUserName();

    UserSurname getRandomUserSurname();

    @Transactional
    void safeNewName(User user, UserName userName, UserSurname userSurname);

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

    void setPasswordRestoringToken(User user);

    void setRole(User user, Role role);

    @Transactional
    void changePassword(User user, String password);


    @Transactional
    void increaseUserRating(User user, int rating);

    List<String> getAllPasswordResetTokens();

    boolean passwordResetTokenExists(String token);

    void setNewPasswordForUserWithToken(String token, String newPassword);

//    @Transactional
//    void changeRatingStatus(User user);

}
