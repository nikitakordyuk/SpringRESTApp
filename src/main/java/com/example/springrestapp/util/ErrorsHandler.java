package com.example.springrestapp.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ErrorsHandler {
    public static String getErrors(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();

        List<ObjectError> errors = bindingResult.getAllErrors();

        for (ObjectError error : errors) {
            errorMessage
                    .append(error.getObjectName())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }
        return errorMessage.toString();
    }
}
