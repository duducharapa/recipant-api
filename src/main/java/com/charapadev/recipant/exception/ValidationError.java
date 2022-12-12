package com.charapadev.recipant.exception;

public record ValidationError(
    String code,
    String message
) {
}
