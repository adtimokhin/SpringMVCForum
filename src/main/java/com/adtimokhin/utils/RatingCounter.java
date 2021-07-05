package com.adtimokhin.utils;

import org.springframework.stereotype.Component;

/**
 * @author adtimokhin
 * 05.07.2021
 **/

@Component
public class RatingCounter {

     public final static int COMMENT_RATING = 7;
     public final static int ANSWER_RATING = 3;
     public final static int LIKE_RATING = 1;
     public final static int UNLIKE_RATING = -1;
     public final static int TOPIC_RATING = 20;
     public final static int REPORT_RATING = 2;


    //TODO: add in future statuses. Like gold, silver, bronze.

     public final static int GOLD_RATING = 140;
     public final static int SILVER_RATING = 60;




}
