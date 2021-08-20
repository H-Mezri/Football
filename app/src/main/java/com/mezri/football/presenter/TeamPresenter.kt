package com.mezri.football.presenter

import com.mezri.football.data.network.repository.RepositoryInterface
import com.mezri.football.data.network.repository.RepositoryResponse
import com.mezri.football.ui.fragment.TeamFragmentInterface
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TeamPresenter(
    private var view: TeamFragmentInterface?,
    private val repository: RepositoryInterface
) : BasePresenter(), TeamPresenterInterface {

    // Coroutines
    private var job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    /**
     * Load team details
     * NO need for this method for the moment
     * We already have all needed info about the team from
     * the precedent fragment
     */
    override fun loadTeamDetails(teamName: String) {
        view?.displayLoader(true)
        scope.launch(Dispatchers.IO) {
            val result = repository.loadTeamDetails(getFormattedRequestString(teamName))
            withContext(Dispatchers.Main) {
                when (result) {
                    is RepositoryResponse.Success -> {
                        view?.displayTeam(result.data)
                    }
                    is RepositoryResponse.Failure -> {
                        view?.displayError()
                    }
                }
                view?.displayLoader(false)
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        view = null
    }
}