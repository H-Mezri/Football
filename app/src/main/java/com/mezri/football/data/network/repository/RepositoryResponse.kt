package com.mezri.football.data.network.repository

sealed class RepositoryResponse<T> {
    class Success<T>(val data: T) : RepositoryResponse<T>()
    class Failure<T>(val error: ErrorCode) : RepositoryResponse<T>()
}

enum class ErrorCode {
    NO_INTERNET_CONNEXION,
    UNKNOWN_ERROR,
    NO_RESULT_FOUND
}