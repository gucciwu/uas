package com.mszq.uas.sso.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionAdvice {

    final String ERROR_PATH = "error_page.html";

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handleAppSecretMatchException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request,
                                              HttpServletResponse response) {
        String msg = ex.getMessage();
        try {
            response.sendRedirect(ERROR_PATH + "?msg=" + Base64.encodeBase64String(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(ServletException.class)
    public void handleServletException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request,
                                              HttpServletResponse response) {
        String msg = ex.getMessage();
        try {
            response.sendRedirect(ERROR_PATH + "?msg=" + Base64.encodeBase64String(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(NumberFormatException.class)
    public void handleNumberFormatException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request,
                                       HttpServletResponse response) {
        String msg = ex.getMessage();
        try {
            response.sendRedirect(ERROR_PATH + "?msg=" + Base64.encodeBase64String(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(IOException.class)
    public void handleIOException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request,
                                            HttpServletResponse response) {
        String msg = ex.getMessage();
        try {
            response.sendRedirect(ERROR_PATH + "?msg=" + Base64.encodeBase64String(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
