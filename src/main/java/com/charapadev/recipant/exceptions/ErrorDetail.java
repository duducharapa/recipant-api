package com.charapadev.recipant.exceptions;

import java.util.List;
import java.util.Map;

public record ErrorDetail(
    long timestamp,
    int status,
    String title,
    String detail,
    String path,
    Map<String, List<ValidationError>> errors
) {
}
