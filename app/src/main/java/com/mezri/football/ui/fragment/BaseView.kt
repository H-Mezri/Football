package com.mezri.football.ui.fragment

interface BaseView<T> {
    fun initPresenter(presenter: T)
}