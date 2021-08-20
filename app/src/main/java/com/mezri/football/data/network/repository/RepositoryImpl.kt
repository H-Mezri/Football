package com.mezri.football.data.network.repository

import com.mezri.football.data.model.Team
import com.mezri.football.data.network.RetrofitClient
import com.mezri.football.data.network.TeamsAPI
import com.mezri.football.data.network.dto.*
import java.lang.Exception

class Repository : RepositoryInterface {

    private val networkService by lazy {
        RetrofitClient.getNetworkClient().create(TeamsAPI::class.java)
    }

    override suspend fun loadAllLeagues(): RepositoryResponse<List<String>> {
        return try {
            val requestResult = networkService.getAllLeagues()
            RepositoryResponse.Success(mapLeagues(requestResult))
        } catch (e: Exception) {
            RepositoryResponse.Failure(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun loadTeamDetails(teamName: String): RepositoryResponse<Team> {
        return try {
            val requestResult = networkService.searchTeamByName(teamName)
            val teamResult = mapTeams(requestResult.teams).firstOrNull()
            teamResult?.let {
                RepositoryResponse.Success(it)
            } ?: run {
                RepositoryResponse.Failure(ErrorCode.UNKNOWN_ERROR)
            }
        } catch (e: Exception) {
            RepositoryResponse.Failure(ErrorCode.UNKNOWN_ERROR)
        }
    }

    private fun mapLeagues(networkTeamsList: RequestLeaguesDTO): List<String> {
        val teamDataMapper: DTOMapper<LeagueDTO, String> =
            object : DTOMapper<LeagueDTO, String> {
                override fun map(input: LeagueDTO): String {
                    return input.strLeague
                }
            }

        return networkTeamsList.leagues.map { teamDataMapper.map(it) }
    }

    override suspend fun loadLeagueTeams(leagueName: String): RepositoryResponse<List<Team>> {
        return try {
            val requestResult = networkService.searchLeagueTeams(leagueName)
            requestResult.teams?.let {
                RepositoryResponse.Success(mapTeams(it))
            } ?: run {
                RepositoryResponse.Failure(ErrorCode.NO_RESULT_FOUND)
            }
        } catch (e: Exception) {
            RepositoryResponse.Failure(ErrorCode.UNKNOWN_ERROR)
        }
    }

    private fun mapTeams(networkTeamsList: List<TeamDTO>): List<Team> {
        val teamDataMapper: DTOMapper<TeamDTO, Team> =
            object : DTOMapper<TeamDTO, Team> {
                override fun map(input: TeamDTO): Team {
                    return Team(input)
                }
            }

        return networkTeamsList.map { teamDataMapper.map(it) }
    }
}