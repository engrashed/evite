package com.sample.evite.core;

import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/***
 * Global exception handler for handling all type of exception in one class.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler({ValidationException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object badArgumentHandler(ValidationException e) {
        log.info(e.getMessage());
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                String message = ((PathImpl) item.getPropertyPath()).getLeafNode().getName() + item.getMessage();
                return CommonResponse.failed(HttpStatus.BAD_REQUEST, message);
            }
        }
        return CommonResponse.failed(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object badArgumentHandler(MethodArgumentNotValidException e) {
        log.info(e.getMessage());
        List<String> errorFields = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errorFields.add(error.getField());
            errorMessages.add(error.getDefaultMessage());
        }
        for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
            errorFields.add(error.getObjectName());
            errorMessages.add(error.getDefaultMessage());
        }
        return CommonResponse.failed(HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object badArgumentHandler(RuntimeException e, WebRequest webRequest) {
        log.warn(e.getMessage());
        return CommonResponse.failed(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({IOException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object seriousHandler(IOException e) {
        return CommonResponse.failed(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object seriousHandler(HttpMessageNotReadableException e) {
        return CommonResponse.failed(HttpStatus.BAD_REQUEST, "Invalid JSON input");
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object seriousHandler(Exception e) {
        e.printStackTrace();
        return CommonResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

    }

    @ExceptionHandler({ServiceException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object serviceHandler(ServiceException e) {

        if (StringUtils.isEmpty(e.getErrorCode())) {
            return CommonResponse.failed(e.getErrorCode(), HttpStatus.BAD_REQUEST, e.getMessage());
        }
        if (!ObjectUtils.isEmpty(e.getErrorData())) {
            return CommonResponse.failed(e.getErrorCode(), HttpStatus.OK, "", e.getErrorData());
        }
        return CommonResponse.failed(e.getErrorCode(), HttpStatus.OK, "");
    }
}
