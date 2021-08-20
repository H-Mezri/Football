package com.mezri.football.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import com.mezri.football.R
import com.mezri.football.data.model.Team
import com.mezri.football.data.network.repository.Repository
import com.mezri.football.databinding.TeamDetailsFragmentBinding
import com.mezri.football.presenter.TeamPresenter
import com.mezri.football.presenter.TeamPresenterInterface
import com.mezri.football.utils.glide.load
import kotlinx.android.synthetic.main.team_details_fragment.*

class TeamFragment : BaseFragment(), TeamFragmentInterface {

    companion object {
        private const val TEAM_KEY = "team_key"
        fun newInstance(team: Team): TeamFragment {
            val fragment = TeamFragment()
            val bundle = Bundle()
            bundle.putParcelable(TEAM_KEY, team)
            fragment.arguments = bundle
            return fragment
        }
    }

    // view data binding
    private lateinit var fragmentBinding: TeamDetailsFragmentBinding

    // fragment presenter
    private var presenter: TeamPresenterInterface? = null

    // team info
    var team: Team? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // shared elements configuration
        postponeEnterTransition()
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        enterTransition =
            TransitionInflater.from(context)
                .inflateTransition(android.R.transition.no_transition)

        // init presenter
        initPresenter(TeamPresenter(this, Repository()))
        team = arguments?.getParcelable(TEAM_KEY)
    }

    override fun getRootView(): View? = rootLayout

    override fun displayError() {
        displaySnackBar()
    }

    override fun displayTeam(team: Team) {
        fragmentBinding.team = team
    }

    override fun displayLoader(isLoadingData: Boolean) {
        if (isLoadingData) {
            pb_teams_loading.visibility = View.VISIBLE
        } else {
            pb_teams_loading.visibility = View.GONE
        }
    }

    override fun initPresenter(presenter: TeamPresenterInterface) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.team_details_fragment, container, false
        )
        fragmentBinding.team = team
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        initUI()
    }

    private fun initUI() {
        // init toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        setHasOptionsMenu(true)
        team?.let {
            displayLoader(true)
            team?.let { safeTeam ->
                // shared elements configuration
                ViewCompat.setTransitionName(imgAlbumCover, safeTeam.id)

                // load album cover
                imgAlbumCover.load(
                    safeTeam.logoURL,
                    true
                ) {
                    displayLoader(false)
                    startPostponedEnterTransition()
                }
                img_team_banner.load(safeTeam.bannerURL) {}

            }
        } ?: kotlin.run {
            displaySnackBar(Snackbar.LENGTH_INDEFINITE)
            displayLoader(false)
        }
    }

    /**
     * Handle option menu click
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}