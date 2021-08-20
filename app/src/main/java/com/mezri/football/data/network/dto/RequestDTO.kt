package com.mezri.football.data.network.dto

data class RequestLeaguesDTO (val leagues: List<LeagueDTO>)
data class RequestTeamsDTO (val teams: List<TeamDTO>?)
data class RequestTeamDetailsDTO (val teams: List<TeamDTO>)