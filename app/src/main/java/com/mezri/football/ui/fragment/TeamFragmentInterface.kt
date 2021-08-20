package com.mezri.football.ui.fragment

import com.mezri.football.data.model.Team
import com.mezri.football.presenter.SearchPresenterInterface
import com.mezri.football.presenter.TeamPresenterInterface

interface TeamFragmentInterface : BaseView<TeamPresenterInterface> {
    fun displayError()
    fun displayTeam(team: Team)
    fun displayLoader(isLoadingData: Boolean)
}