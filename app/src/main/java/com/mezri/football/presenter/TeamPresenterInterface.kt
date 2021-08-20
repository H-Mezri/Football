package com.mezri.football.presenter

interface TeamPresenterInterface : BasePresenterInterface {
    fun loadTeamDetails(teamName: String)
}