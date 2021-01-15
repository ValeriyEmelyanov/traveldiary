package com.example.traveldiary.exception;

public enum ErrorMessages {
    BAD_REQUEST("Bad request"),
    BAD_LOGIN_PASSWORD("Bad login or password"),
    BAD_PASSWORD_NOT_MATCHING("Password not matching"),
    WRONG_USER("Wrong user"),
    NO_PERMISSIONS("No permissions"),
    WRONG_OLD_PASSWORD("Wrong old password"),
    NOT_FOUND("Not found"),
    USERNAME_ALREADY_TAKEN("The username is already taken"),
    NULL_USER_ID("User id should not be null"),
    NULL_USERNAME("Username should not be null"),
    NULL_USER_OBJECT("User object should not be null"),
    NULL_USER_AUTHORITIES("User authorities should not be null"),
    NULL_PASSWORD_DATA("Password data should not be null"),
    NULL_TIMESTAMP("Timestamp should not be null"),
    NULL_DESCRIPTION("Description should not be null"),
    NULL_PASSWORD("Password should not be null"),
    NULL_EXPENSE_TYPE_ID("Expense type id should not be null"),
    NULL_EXPENSE_TYPE_OBJECT("Expense type object should not be null"),
    NULL_TRAVEL_ID("Travel id should not be null"),
    NULL_TRAVEL_OBJECT("Travel object should not be null");

    private final String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
