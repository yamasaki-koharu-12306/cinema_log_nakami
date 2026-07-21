package com.example.demo.controller;
import org.springframework.ui.Model; import org.springframework.web.bind.annotation.ControllerAdvice; import org.springframework.web.bind.annotation.ExceptionHandler; import java.util.NoSuchElementException;
@ControllerAdvice public class GlobalExceptionHandler {@ExceptionHandler(NoSuchElementException.class) String notFound(NoSuchElementException e,Model m){m.addAttribute("message",e.getMessage());return "error/404";}}
