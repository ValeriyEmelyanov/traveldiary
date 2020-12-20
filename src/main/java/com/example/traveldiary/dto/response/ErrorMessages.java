package com.example.traveldiary.dto.response;

public enum ErrorMessages {
    BAD_REQUEST("Bad request"),
    BAD_LOGIN_PASSWORD("Bad login or password"),
    BAD_PASSWORD_NOT_MATCHING("Password not matching"),
    WRONG_USER("Wrong user"),
    NO_PERMISSIONS("No permissions"),
    WRONG_OLD_PASSWORD("Wrong old password"),
    NOT_FOUND("Not found");

    private final String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
