package com.adtimokhin.services.user.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.user.UserNameRepository;
import com.adtimokhin.repositories.user.UserRepository;
import com.adtimokhin.repositories.user.UserSurnameRepository;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserNameRepository userNameRepository;

    @Autowired
    private UserSurnameRepository userSurnameRepository;


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
    public void addUser(User user, Role... roles) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(roles)));
        assignUserFullName(user);
        repository.save(user);

    }

    @Override
    public void assignUserFullName(User user) {
        // Right now full name creation for a single user will occur in this method. In future this can be placed into
        // UserName and UserSurname Services

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
        user.setBanned(true);
        repository.save(user);
    }

    @Override
    public void unBanUser(User user) {
        user.setBanned(false);
        repository.save(user);
    }


}
