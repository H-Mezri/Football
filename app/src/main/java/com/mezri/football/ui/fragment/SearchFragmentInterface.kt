package com.mezri.football.ui.fragment

import com.mezri.football.data.model.Team
import com.mezri.football.presenter.PresenterError
import com.mezri.football.presenter.SearchPresenterInterface

interface SearchFragmentInterface : BaseView<SearchPresenterInterface> {
    fun displayError(error: PresenterError)
    fun displayTeams(newTeamList: List<Team>)
    fun displayLoader(isLoadingData: Boolean)
}
