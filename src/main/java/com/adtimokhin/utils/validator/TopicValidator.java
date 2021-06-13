package com.adtimokhin.utils.validator;

import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.services.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.adtimokhin.utils.validator.GeneralValidations.isFieldEmpty;

/**
 * @author adtimokhin
 * 24.04.2021
 **/

@Component
public class TopicValidator{

    @Autowired
    private TopicService topicService;


    public boolean supports(Class<?> clazz) {
        return Topic.class.equals(clazz);
    }


    public ArrayList<String> validate(String topic, String description) {
        ArrayList<String> errors = new ArrayList<>();
        if (isFieldEmpty(topic)){
            errors.add("Your topic title is empty");
        }
        if(isFieldEmpty(description)){
            errors.add("Your topic description is empty");
        }

        if (!errors.isEmpty()){
            return errors;
        }

        if(topicService.getTopic(topic) != null){
            errors.add("Topic with such a name already exists. Please chose another name");
        }

        if (!errors.isEmpty()){
            return errors;
        }
        return null;
    }
}
