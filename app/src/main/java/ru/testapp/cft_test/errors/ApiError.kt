package ru.testapp.cft_test.errors

class ApiError(
    code: Int,
    message: String
) : RuntimeException(message)