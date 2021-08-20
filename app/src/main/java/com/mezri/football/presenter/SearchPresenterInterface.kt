package com.mezri.football.presenter

import com.mezri.football.data.model.Team

interface SearchPresenterInterface : BasePresenterInterface {
    fun getTeamListCache(): List<Team>
    fun getAllLeagues(): List<String>
    fun loadAllLeagues()
    fun loadTeamsByLeagueName(leagueName: String)
}