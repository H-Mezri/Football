package com.mezri.football.ui.activity

import android.view.View
import com.mezri.football.data.model.Team

interface MainActivityInterface {
    fun showTeamDetailsFragment(view: View, team: Team)
}