package com.mezri.football.presenter

import com.mezri.football.data.model.Team
import com.mezri.football.data.network.repository.ErrorCode
import com.mezri.football.data.network.repository.RepositoryInterface
import com.mezri.football.data.network.repository.RepositoryResponse
import com.mezri.football.ui.fragment.SearchFragmentInterface
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchPresenter(
    private var view: SearchFragmentInterface?,
    private val repository: RepositoryInterface
) : BasePresenter(), SearchPresenterInterface {

    /**
     * List are used to save network data ( cache )
     * In a bigger project data can be saved in Room Data base or SharedPref
     */
    private val teamsListCached = mutableListOf<Team>()
    private val allLeaguesName = mutableListOf<String>()

    override fun getTeamListCache(): List<Team> = teamsListCached
    override fun getAllLeagues(): List<String> = allLeaguesName


    // Coroutines
    private var job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)


    override fun loadTeamsByLeagueName(leagueName: String) {
        view?.displayLoader(true)
        scope.launch(Dispatchers.IO) {
            val result = repository.loadLeagueTeams(getFormattedRequestString(leagueName))
            withContext(Dispatchers.Main) {
                when (result) {
                    is RepositoryResponse.Success -> {
                        teamsListCached.clear()
                        teamsListCached.addAll(result.data)
                        view?.displayTeams(result.data)
                    }
                    is RepositoryResponse.Failure -> {
                        val error = when(result.error) {
                            ErrorCode.NO_RESULT_FOUND -> PresenterError.NO_RESULT_FOUND
                            else -> PresenterError.UNKNOWN_ERROR
                        }
                        view?.displayError(error)
                    }
                }
                view?.displayLoader(false)
            }
        }
    }

    override fun loadAllLeagues() {
        scope.launch(Dispatchers.IO) {
            val result = repository.loadAllLeagues()
            withContext(Dispatchers.Main) {
                when (result) {
                    is RepositoryResponse.Success -> {
                        allLeaguesName.addAll(result.data)
                    }
                    is RepositoryResponse.Failure -> {
                        view?.displayError(PresenterError.UNKNOWN_ERROR)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        view = null
    }
}