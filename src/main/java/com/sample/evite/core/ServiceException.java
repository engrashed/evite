package com.sample.evite.core;

import com.sample.evite.enums.ErrorLevel;
import lombok.Data;

import java.text.MessageFormat;
import java.util.Map;

@Data
public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = 1234051936007357216L;

    private ErrorLevel errorLevel;
    private String errorCode;
    private Map<String, Object> errorData;

    public ServiceException(ErrorStatus errorStatus) {
        super(errorStatus.getErrorMessage());
        this.errorLevel = ErrorLevel.INFO;
        this.errorCode = null;
    }

    public ServiceException(ErrorStatus errorStatus, Object... params) {
        super(MessageFormat.format(errorStatus.getErrorMessage(), params));
        this.errorLevel = ErrorLevel.INFO;
        this.errorCode = null;
    }

    public ServiceException(String errorCode, ErrorStatus errorStatus) {
        super(errorStatus.getErrorMessage());
        this.errorLevel = ErrorLevel.INFO;
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode, ErrorStatus errorStatus, Object... params) {
        super(MessageFormat.format(errorStatus.getErrorMessage(), params));
        this.errorLevel = ErrorLevel.INFO;
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorLevel errorLevel, String errorCode, ErrorStatus errorStatus) {
        super(errorStatus.getErrorMessage());
        this.errorLevel = errorLevel;
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorLevel errorLevel, String erroCode, ErrorStatus errorStatus, Object... params) {
        super(MessageFormat.format(errorStatus.getErrorMessage(), params));
        this.errorLevel = errorLevel;
        this.errorCode = erroCode;
    }

    /*public ServiceException(ErrorCode errorCode, Object... params) {
        super(MessageFormat.format(errorCode.getErrorMessage(), params));
        this.errorLevel = ErrorLevel.INFO;
        this.errorCode = errorCode.getErrorCode();
    }
    public ServiceException(ErrorCode errorCode, Map<String, Object> errorData, Object... params) {
        super(MessageFormat.format(errorCode.getErrorMessage(), params));
        this.errorLevel = ErrorLevel.INFO;
        this.errorCode = errorCode.getErrorCode();
        this.errorData = errorData;
    }*/
}
