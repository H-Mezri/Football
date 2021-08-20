package com.mezri.football.presenter

abstract class BasePresenter {
    protected fun getFormattedRequestString(request: String): String {
        return request.replace(" ", "_")
    }
}