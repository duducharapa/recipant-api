package com.charapadev.recipant.exceptions;

public record ValidationError(
    String code,
    String message
) {
}
