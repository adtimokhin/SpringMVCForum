package com.adtimokhin.services.report.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.report.Cause;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.report.CauseRepository;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.report.CauseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 14.06.2021
 **/

@Component
public class CauseServiceImpl implements CauseService {

    @Autowired
    private CauseRepository repository;

    @Autowired
    private ContextProvider contextProvider;

    private static final Logger logger = Logger.getLogger("file");


    @Override
    public void addCause(String causeText) {
        Cause cause = new Cause();
        cause.setTitle(causeText);

        repository.save(cause);

    }

    @Override
    public List<Cause> getAllCauses() {
        return repository.findAll();
    }

    @Override
    public Cause getOrAddCause(String causeText) {
        if (causeText.endsWith(",")) {
            causeText = causeText.substring(0, causeText.length() - 1);
        } else if (causeText.startsWith(",")) {
            causeText = causeText.substring(1); // todo: test
        }
        Cause cause = repository.findByTitle(causeText);
        if (cause == null) {
            addCause(causeText);
            cause = repository.findByTitle(causeText);
            return cause;
        }
        return cause;

    }

    @Override
    public List<Cause> getAllBasicCauses() {
        return repository.findAllByBasicIsTrue();
    }

    @Override
    public void makeCauseBasic(Cause cause) {
        if (cause == null) {
            logger.info("Tried to make a null cause basic");
            return;
        }
        User user = contextProvider.getUser();
        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            cause.setBasic(true);
            repository.save(cause);
        } else {
            logger.info("User with id " + user.getId() + " tried to change cause to be basic, but he was not an admin");
        }
    }

    @Override
    public void makeCauseBasic(long causeId) {
        Cause cause = repository.findById(causeId);
        makeCauseBasic(cause);
    }
}
