package com.udacity.jwdnd.course1.cloudstorage;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exception, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("errorMessage", "File cannot exceed 50MB.");
        return modelAndView;
    }
}
