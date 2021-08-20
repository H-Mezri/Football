package com.mezri.football.presenter

interface BasePresenterInterface {
    fun onDestroy()
}

enum class PresenterError {
    NO_RESULT_FOUND,
    UNKNOWN_ERROR
}