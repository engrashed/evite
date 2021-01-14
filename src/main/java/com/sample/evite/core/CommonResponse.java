package com.sample.evite.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CommonResponse<T> {
    private long code;
    private String errCode;
    private String[] messages;
    private List errItems;
    private T result;


    protected CommonResponse(long code, String message, T data) {
        this.code = code;
        if (messages == null) {
            messages = new String[]{message};
        }
        result = data;
    }

    protected CommonResponse(String customErrorCode, long code, String message, T data) {
        this.code = code;
        this.errCode = customErrorCode;
        if (messages == null) {
            messages = new String[]{message};
        }
        result = data;
    }

    protected CommonResponse(long code, String[] messages, List errItems) {
        this.code = code;
        this.messages = messages;
        this.errItems = errItems;
    }

    /**
     * return success
     *
     * @param data data required)
     */
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<T>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * @param data    data required
     * @param message message required
     */
    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<T>(HttpStatus.OK.value(), message, data);
    }

    /**
     * success message with custom message
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> CommonResponse<T> success(String message) {
        return new CommonResponse<T>(HttpStatus.OK.value(), message, null);
    }

    /**
     * return error with code
     *
     * @param errorCode error code in enum is required
     */
    public static <T> CommonResponse<T> failed(HttpStatus errorCode) {
        return new CommonResponse<T>(errorCode.value(), errorCode.getReasonPhrase(), null);
    }

    /**
     * override the default message
     *
     * @param errorCode error code by Error Code enum
     * @param message   custom message provided by user
     */
    public static <T> CommonResponse<T> failed(String customErrCode, HttpStatus errorCode, String message) {
        return new CommonResponse<T>(customErrCode,errorCode.value(), message, null);
    }
    /**
     * override the default message
     *
     * @param errorCode error code by Error Code enum
     * @param message   custom message provided by user
     */
    public static <T> CommonResponse<T> failed(String customErrCode, HttpStatus errorCode, String message, T data) {
        return new CommonResponse<T>(customErrCode,errorCode.value(), message, data);
    }

    /**
     * override the default message
     *
     * @param errorCode error code by Error Code enum
     * @param message   custom message provided by user
     */
    public static <T> CommonResponse<T> failed(HttpStatus errorCode, String message) {
        return new CommonResponse<T>(errorCode.value(), message, null);
    }

    /**
     * override the default message
     *
     * @param errorCode error code by Error Code enum
     * @param messages  custom message provided by user
     */
    public static <T> CommonResponse<T> failed(HttpStatus errorCode, String[] messages, List errItems) {
        return new CommonResponse<T>(errorCode.value(), messages, errItems);
    }

    /**
     * return failed error message
     *
     * @param message error message required
     */
    public static <T> CommonResponse<T> failed(String message) {
        return new CommonResponse<T>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    /**
     * common failed error
     */
    public static <T> CommonResponse<T> failed() {
        return failed(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * validation failed error
     */
    public static <T> CommonResponse<T> validateFailed() {
        return failed(HttpStatus.BAD_REQUEST);
    }

    /**
     * validation failed with custom error
     *
     * @param message error message
     */
    public static <T> CommonResponse<T> validateFailed(String message) {
        return new CommonResponse<T>(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    /**
     * unauthorized error
     */
    public static <T> CommonResponse<T> unauthorized(T data) {
        return new CommonResponse<T>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), data);
    }

    /**
     * forbidden error
     */
    public static <T> CommonResponse<T> forbidden(T data) {
        return new CommonResponse<T>(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), data);
    }


}