package com.mezri.football.data.network

import com.mezri.football.data.network.dto.*
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamsAPI {
    /**
     * Get request to search teams by name
     */
    @GET("all_leagues.php")
    suspend fun getAllLeagues(): RequestLeaguesDTO

    /**
     * Get request to search league teams by name
     */
    @GET("search_all_teams.php")
    suspend fun searchLeagueTeams(@Query("l") leagueName: String): RequestTeamsDTO

    /**
     * Get request to search team by name
     */
    @GET("searchteams.php")
    suspend fun searchTeamByName(@Query("t") teamName: String): RequestTeamDetailsDTO
}