package com.adtimokhin.services.user.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.company.Token;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.user.UserNameRepository;
import com.adtimokhin.repositories.user.UserRepository;
import com.adtimokhin.repositories.user.UserSurnameRepository;
import com.adtimokhin.services.company.TokenService;
import com.adtimokhin.services.user.UserService;
import com.adtimokhin.utils.TokenGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Component
public class UserServiceImpl implements UserService {
    //Repositories
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserNameRepository userNameRepository;

    @Autowired
    private UserSurnameRepository userSurnameRepository;


    //Services
    @Autowired
    private TokenService tokenService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    private Random random = new Random();

    @Autowired
    private TokenGenerator tokenGenerator;

    private static final Logger logger = Logger.getLogger("file");
    private static final Logger adminLogger = Logger.getLogger("admin");

    @Override
    public User getUser(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User getUser(long id) {
        return repository.findById(id);
    }

    @Override
    public void addUser(User user, Role... roles) {

        if (user == null) {
            logger.info("Tried to add a null user");
            return;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(roles)));
        assignUserFullName(user);
        user.setEmailVerificationToken(tokenGenerator.generateEmailVerificationToken());
        repository.save(user);

    }

    @Override
    public void addOrganizationMember(User user, String tokenValue) {
        if (user == null) {
            logger.info("Tried to add a null user as an organization member");
            return;
        }

        Token token = tokenService.getToken(tokenValue);
        user.setToken(token);
        addUser(user, Role.ROLE_ORGANIZATION_MEMBER);

        tokenService.setUser(token, user);
    }

    @Override
    public void assignUserFullName(User user) {
        if (user == null) {
            logger.info("Tried to assign a full name to a null user");
            return;
        }

        //Todo: So that we don't need to recount number of names, we have to assign the size to the services of UserName and UserSurname
        long countNames = userNameRepository.count();
        long countSurnames = userSurnameRepository.count();

        long randomNamePosition = countNames - random.nextInt(Math.toIntExact(countNames));
        long randomSurnamePosition = countSurnames - random.nextInt(Math.toIntExact(countSurnames));

        user.setUserName(userNameRepository.getById(randomNamePosition));
        user.setUserSurname(userSurnameRepository.getById(randomSurnamePosition));
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


}
