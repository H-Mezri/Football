package com.mezri.football.data.network.repository

import com.mezri.football.data.model.Team

interface RepositoryInterface {
    /**
     * Get all league teams by name
     * @param leagueName name of the league
     */
    suspend fun loadLeagueTeams(leagueName: String): RepositoryResponse<List<Team>>

    /**
     * Get all leagues
     */
    suspend fun loadAllLeagues(): RepositoryResponse<List<String>>

    /**
     * Get team details
     * @param teamName name of the team
     */
    suspend fun loadTeamDetails(teamName: String): RepositoryResponse<Team>
}