package com.adtimokhin.services.user.impl;

import com.adtimokhin.models.user.UserName;
import com.adtimokhin.models.user.UserSurname;
import com.adtimokhin.repositories.user.UserNameRepository;
import com.adtimokhin.repositories.user.UserSurnameRepository;
import com.adtimokhin.services.user.UserFullNameService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author adtimokhin
 * 13.06.2021
 **/

@Component
public class UserFullNameServiceImpl implements UserFullNameService {

    @Autowired
    private UserNameRepository userNameRepository;

    @Autowired
    private UserSurnameRepository userSurnameRepository;

    private static boolean isNumberOfUserNamesChanged = true;
    private static boolean isNumberOfUserSurnamesChanged = true;

    private static long numberOfUserNames;
    private static long numberOfUserSurnames;

    private static final Logger logger = Logger.getLogger("file");

    @Override
    public UserName getUserName(long id) {
        return userNameRepository.getById(id);
    }

    @Override
    public UserName getUserName(String name) {
        if (name == null) {
            logger.info("Tried to get a null name");
            return null;
        }
        if (name.isEmpty()) {
            logger.info("Tried to get an empty name");
            return null;
        }
        return userNameRepository.getByName(name);
    }

    @Override
    public UserSurname getUserSurname(long id) {
        return userSurnameRepository.getById(id);
    }

    @Override
    public UserSurname getUserSurname(String surname) {
        if (surname == null) {
            logger.info("Tried to get a null surname");
            return null;
        }
        if (surname.isEmpty()) {
            logger.info("Tried to get an empty surname");
            return null;
        }
        return userSurnameRepository.getBySurname(surname);
    }

    @Override
    public String formatNameOrSurname(String name) {
        if (name == null) {
            logger.info("Tried to format a null name");
            return null;
        }
        if (name.isEmpty()) {
            logger.info("Tried to format an empty name");
            return null;
        }

        char[] letters = name.toCharArray();

        StringBuilder formattedName = new StringBuilder();
        for (int i = 0; i < letters.length; i++) {
            if (i == 0) {
                formattedName.append(Character.toUpperCase(letters[i]));
            } else {
                formattedName.append(Character.toLowerCase(letters[i]));
            }
        }
        return formattedName.toString();

    }

    @Override
    public void addUserName(String name) {
        if (name == null) {
            logger.info("Tried to add a null name");
            return;
        }
        if (name.isEmpty()) {
            logger.info("Tried to add an empty name");
            return;
        }

        UserName userName = new UserName();
        userName.setName(name);

        userNameRepository.save(userName);

        numberOfUserNames = getNumberOfUserNames() + 1;

    }

    @Override
    public void addUserSurname(String surname) {
        if (surname == null) {
            logger.info("Tried to add a null surname");
            return;
        }
        if (surname.isEmpty()) {
            logger.info("Tried to add an empty surname");
            return;
        }

        UserSurname userSurname = new UserSurname();
        userSurname.setSurname(surname);

        userSurnameRepository.save(userSurname);

        numberOfUserSurnames = getNumberOfUserSurnames() + 1;
    }

    @Override
    public long getNumberOfUserNames() {
        if (isNumberOfUserNamesChanged) {
            numberOfUserNames = userNameRepository.count();
            isNumberOfUserNamesChanged = false;
        }
        return numberOfUserNames;
    }

    @Override
    public long getNumberOfUserSurnames() {
        if (isNumberOfUserSurnamesChanged) {
            numberOfUserSurnames = userSurnameRepository.count();
            isNumberOfUserSurnamesChanged = false;
        }
        return numberOfUserSurnames;
    }
}
