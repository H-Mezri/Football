package com.mezri.football.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import com.mezri.football.R
import com.mezri.football.data.model.Team
import com.mezri.football.ui.fragment.TeamFragment
import com.mezri.football.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity(), MainActivityInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    SearchFragment.newInstance(),
                    SearchFragment::class.java.simpleName
                )
                .commitNow()
        }
    }

    override fun showTeamDetailsFragment(view: View, team: Team) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // get new instance of fragment
        val fragment = TeamFragment.newInstance(team)
        // add shared element to fragment transaction
        fragmentTransaction.addSharedElement(view, ViewCompat.getTransitionName(view)!!)

        fragmentTransaction.replace(
            R.id.container,
            fragment,
            TeamFragment::class.java.simpleName
        )

        fragmentTransaction.addToBackStack(TeamFragment::class.java.simpleName)

        fragmentTransaction.commit()
    }
}
