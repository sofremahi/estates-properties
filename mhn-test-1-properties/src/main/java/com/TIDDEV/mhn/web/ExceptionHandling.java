package com.TIDDEV.mhn.web;

import com.TIDDEV.mhn.common.Message;
import com.TIDDEV.mhn.common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandling extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

@ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handleBusinessException(BusinessException e) {
        Response response = new Response<>(new Message(getMessageBundle(e.getTitle(), e.getParam())));
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    private String getMessageBundle(String message, Object[] param) {
        return messageSource.getMessage(message, param, new Locale("default"));
    }
}
