package com.sample.evite.core;

public enum ErrorStatus {
    //Event
    Err_CREATE("Event does not created"),
    Err_DELETE("Event is not deleted"),
    Err_DATA("Data not found"),
    Err_TIMEZONE("Time Zone is not matched"),
    //User
    Err_EXIST("User already exist"),
    // User Event
    Err_EVENTCREATE("User Event is not created"),
    Err_EVENTDELETE("User Event is not deleted"),
    //Common
    Err_MAIL("Mail send error");
    private final String errMsg;

    ErrorStatus(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrorMessage() {
        return errMsg;
    }
}
