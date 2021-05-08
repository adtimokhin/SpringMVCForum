package com.adtimokhin.controllers;

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

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String exception404(){
        System.out.println("ERRor 404!!!!!");
        return "error/404";
    }
}
