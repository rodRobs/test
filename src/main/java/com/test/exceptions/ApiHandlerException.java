package com.test.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.test.utils.MessageError;


@RestControllerAdvice
public class ApiHandlerException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public MessageError notFoundException(HttpServletRequest request, Exception exception) {
        return new MessageError(exception, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({BadRequestException.class})
    @ResponseBody
    public MessageError badRequestException(HttpServletRequest request, Exception exception) {
        return new MessageError(exception, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseBody
    public MessageError unauthorizationException(HttpServletRequest request, Exception exception) {
        return new MessageError(exception, request.getRequestURI());
    }

}
