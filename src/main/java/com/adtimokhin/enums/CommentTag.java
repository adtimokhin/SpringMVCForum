package com.adtimokhin.enums;

/**
 * @author adtimokhin
 * 01.05.2021
 **/

public enum CommentTag {

    SOLUTION,
    PROBLEM_CLARIFICATION,
    SIMILAR_PROBLEM,
    COMMENT_FROM_QUALIFIED_PEOPLE,
    COMMENT_FROM_PARENT;


    public String getTag() {
        return name();
    }
}
