package com.thanhtd.glassstore.core.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    OK(200, "Success"),
    AUTH_SUCCESS(201, "Authentication pass"),

    VALID_OTP(202, "Valid OTP"),

    FAIL(404, "Fail"),

    DUPLICATE_ERROR(420, "Duplicate error"),

    TOKEN_INVALID(402, "Invalid token"),

    TOKEN_VALID(421, "Valid token"),

    INTERNAL_SERVER_ERROR(500, "Internal server error"),

    BLANK_FIELD(422, "Field input is blank"),

    DATA_NULL(423, "Data is null"),

    MISSING_DATA(404, "Data is missing require field"),

    DELETED_DATA(423, "Data is deleted"),

    ID_NULL(424, "Id is null"),

    LOGIN_FAIL(401, "You are not logged in"),

    SIGNUP_FAIL(402, "Create account fail"),

    UNLATCHING_PASSWORD(401, "Password and confirm password do not match"),

    INVALID_EMAIL(401, "Invalid email address"),

    INVALID_CREDENTIAL(401, "Invalid credential"),

    INVALID_OTP(425, "Invalid OTP"),

    NOT_FOUND_USER(404, "Not found user"),

    NOT_FOUND_ROLE(404, "Not found role"),

    NOT_FOUND_PERMISSION(404, "Not found permission"),

    NOT_FOUND_ACTION(404, "Not found user"),

    NOT_FOUND_CITY(404, "Not found city on system"),

    NOT_FOUND_DISTRICT(404, "Not found district on system"),

    NOT_FOUND_WARD(404, "Not found ward on system"),

    NOT_FOUND_CATEGORY(404, "Not found category on system"),

    NOT_FOUND_SHAPE(404, "Not found shape on system"),

    NOT_FOUND_COLOR(404, "Not found color on system"),

    NOT_FOUND_BRAND(404, "Not found brand on system"),

    NOT_FOUND_PRODUCT(404, "not found product on system"),

    NOT_FOUND_IMAGE(404, "Not found image on system"),

    NOT_FOUND_STORE(404, "Not found store on system"),

    NOT_FOUND_DATA(404, "Not found data"),

    ;
    private final int value;
    private final String message;


    private ErrorCode(int value, String message) {
        this.value = value;
        this.message = message;
    }

}