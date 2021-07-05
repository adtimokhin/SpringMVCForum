package com.adtimokhin.services.user.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.company.Token;
import com.adtimokhin.models.user.User;
import com.adtimokhin.models.user.UserName;
import com.adtimokhin.models.user.UserSurname;
import com.adtimokhin.repositories.user.UserRepository;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.company.TokenService;
import com.adtimokhin.services.user.UserFullNameService;
import com.adtimokhin.services.user.UserService;
import com.adtimokhin.utils.EmailSender;
import com.adtimokhin.utils.TokenGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.adtimokhin.utils.validator.UserValidator.isPasswordValid;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Component
public class UserServiceImpl implements UserService {
    //Repositories
    @Autowired
    private UserRepository repository;


    //Services
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserFullNameService userFullNameService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ContextProvider contextProvider;


    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private EmailSender emailSender;

    private static final Logger logger = Logger.getLogger("file");
    private static final Logger adminLogger = Logger.getLogger("admin");

    private Random random = new Random();

    @Override
    public User getUser(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User getUser(long id) {
        return repository.findById(id);
    }

    @Override
    public void addUser(User user, boolean generateRandomName, Role... roles) {

        if (user == null) {
            logger.info("Tried to add a null user");
            return;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(roles)));
        if (generateRandomName) {
            assignUserFullName(user);
        }
        user.setEmailVerificationToken(tokenGenerator.generateEmailVerificationToken());
        repository.save(user);

        emailSender.sentEmailVerificationLetter(user);

    }

    @Override
    public void addOrganizationMember(User user, String tokenValue, String firstName, String lastName) {
        if (user == null) {
            logger.info("Tried to add a null user as an organization member");
            return;
        }

        Token token = tokenService.getToken(tokenValue);
        user.setToken(token);
        addUser(user, false, Role.ROLE_ORGANIZATION_MEMBER);
        //formatting and setting a real name to the organization member.
        firstName = userFullNameService.formatNameOrSurname(firstName);
        lastName = userFullNameService.formatNameOrSurname(lastName);

        UserName userName = userFullNameService.getUserName(firstName);
        if (userName == null) {
            userFullNameService.addUserName(firstName);
            userName = userFullNameService.getUserName(firstName);
        }
        user.setUserName(userName);

        UserSurname userSurname = userFullNameService.getUserSurname(lastName);
        if (userSurname == null) {
            userFullNameService.addUserSurname(lastName);
            userSurname = userFullNameService.getUserSurname(lastName);
        }
        user.setUserSurname(userSurname);

        repository.save(user);

        tokenService.setUser(token, user);
    }

    @Override
    public void assignUserFullName(User user) {
        if (user == null) {
            logger.info("Tried to assign a full name to a null user");
            return;
        }

        user.setUserName(getRandomUserName());
        user.setUserSurname(getRandomUserSurname());
    }

    @Override
    public UserName getRandomUserName() {
        long countNames = userFullNameService.getNumberOfUserNames();
        long randomNamePosition = countNames - random.nextInt(Math.toIntExact(countNames));
        return userFullNameService.getUserName(randomNamePosition);

    }

    @Override
    public UserSurname getRandomUserSurname() {
        long countSurnames = userFullNameService.getNumberOfUserSurnames();
        long randomSurnamePosition = countSurnames - random.nextInt(Math.toIntExact(countSurnames));
        return userFullNameService.getUserSurname(randomSurnamePosition);
    }

    @Override
    public void safeNewName(User user, UserName userName, UserSurname userSurname) {
        if (user == null) {
            logger.info("Tried to assign a new full name to a null user");
            return;
        }
        if (userName == null) {
            logger.info("Tried to assign null UserName");
            return;
        }
        if (userSurname == null) {
            logger.info("Tried to assign null UserSurname");
            return;
        }
        user.setUserName(userName);
        user.setUserSurname(userSurname);
        repository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        if (user == null) {
            logger.info("Tried to delete a null user");
            return;
        }
        repository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public List<User> getAllReportedUsers() {
        return getAllUsers();
    }

    @Override
    public List<User> getAllBannedUsers() {
        return repository.getAllByBanned(true);
    }

    @Override
    public void banUser(User user) {
        if (user == null) {
            logger.info("Tried to ban a null user");
            adminLogger.info("Tried to ban a null user");
            return;
        }
        user.setBanned(true);
        repository.save(user);
    }

    @Override
    public void unBanUser(User user) {
        if (user == null) {
            logger.info("Tried to unban a null user");
            adminLogger.info("Tried to unban a null user");
            return;
        }
        user.setBanned(false);
        repository.save(user);
    }

    @Override
    public boolean isFirstTime(User user) {
        if (user == null) {
            logger.info("Tried find if a null user has logged in before");
            return false;
        }
        return user.isFirstTime();
    }

    @Override
    public void setUserEnteredTheForum(User user) {
        if (user == null) {
            logger.info("Tried log in as a null user");
            return;
        }
        if (!user.isFirstTime()) {
            logger.info("Tried to set not first time to a user with id" + user.getId() + " that already had logged in");
            return;
        }
        if (user.getEmailVerificationToken() != null) {
            logger.info("User with id " + user.getId() + " tried to enter the forum without verifying his/her email");
            return;
        }
        user.setFirstTime(false);
        repository.save(user);
    }

    @Override
    public List<String> getAllEmailVerificationTokens() {
        List<User> users = repository.findAllByEmailVerificationTokenIsNotNull();
        if (users == null) {
            return null;
        }
        List<String> tokens = new ArrayList<>();
        for (User user :
                users) {
            tokens.add(user.getEmailVerificationToken());
        }
        return tokens;
    }

    @Override
    public boolean verifyEmail(String token) {
        if (token == null) {
            logger.info("Tried to verify email with a null token");
            return false;
        }
        if (token.isEmpty()) {
            logger.info("Tried to verify email with a null token");
            return false;
        }

        User user = repository.findByEmailVerificationToken(token);
        if (user == null) {
            logger.info("Tried to verify email with an invalid token " + token);
            return false;
        }
        user.setEmailVerificationToken(null);
        repository.save(user);
        return true;
    }

    @Override
    public void setRole(User user, Role role) {
        if (role.equals(Role.ROLE_ADMIN)) {
            User u = contextProvider.getUser();
            if (u.getRoles().contains(Role.ROLE_ADMIN)) {
                user.setRoles(Collections.singleton(role));
                repository.save(user);
                adminLogger.info("Admin with id " + u.getId() + " had promoted user with id " + user.getId() + " to become an admin");
            } else {
                logger.info("User with id " + u.getId() + " tried to promote user with id " + user.getId() + " to become an admin");
            }
        } else {
            user.setRoles(Collections.singleton(role));
            repository.save(user);
            logger.info("User with id " + user.getId() + " got the role changed to " + role.getRole());
        }
    }

    @Override
    public void changePassword(User user, String password) {
        //TODO: add later email "sentage" to user's email that tells that his password was changed.
        if (user == null) {
            logger.info("Tried to change password for a null user");
            return;
        }
        if (password == null) {
            logger.info("Tried to change password for a user with id " + user.getId() + " to null");
            return;
        }
        if (!isPasswordValid(password)) {
            logger.info("Tried to change password for a user with id " + user.getId() + " to a password that does not match the security requirements");
            return;
        }
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);

        logger.info("User with id" + user.getId() + " had changed his/her password.");

    }

    @Override
    public void increaseUserRating(User user, int rating) {
        if (user == null) {
            logger.info("Tried to increase rating for a null user");
            return;
        }

        user.setRating(user.getRating() + rating);
        repository.save(user);
    }
}
