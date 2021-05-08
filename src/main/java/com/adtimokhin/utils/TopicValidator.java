package com.adtimokhin.utils;

import com.adtimokhin.models.topic.Topic;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author adtimokhin
 * 24.04.2021
 **/

@Component
public class TopicValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Topic.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Topic topic = (Topic) target;
        if (topic.getTopic().isEmpty()){
            errors.rejectValue("topic" , "You should have a topic name");
        }
        if (topic.getDescription().isEmpty()){
            errors.rejectValue("description" , "You should have a topic description");
        }

        //TODO: potentially add another validation to see if there are equal in name topics.

    }
}
