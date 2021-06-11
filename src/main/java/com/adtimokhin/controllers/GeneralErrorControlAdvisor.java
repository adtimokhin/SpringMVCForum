package com.adtimokhin.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author adtimokhin
 * 10.04.2021
 **/
@ControllerAdvice
public class GeneralErrorControlAdvisor {

    private static final Logger logger = Logger.getLogger("file");

    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(NoHandlerFoundException.class)
    public String exception404() {
        logger.error("Tried to access page that does not exist.");
        return "error/404";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(Exception.class)
    public String anyExceptionHandler() {
        logger.error("Internal server error.");
        return "error/500";
    }


}
