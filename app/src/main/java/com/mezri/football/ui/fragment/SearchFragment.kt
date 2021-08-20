package com.mezri.football.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.mezri.football.R
import com.mezri.football.data.model.Team
import com.mezri.football.data.network.repository.Repository
import com.mezri.football.presenter.PresenterError
import com.mezri.football.presenter.SearchPresenter
import com.mezri.football.presenter.SearchPresenterInterface
import com.mezri.football.ui.activity.MainActivityInterface
import com.mezri.football.ui.adapter.TeamsRecyclerAdapter
import com.mezri.football.utils.hideKeyboard
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : BaseFragment(), SearchFragmentInterface {
    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    // fragment presenter
    private var presenter: SearchPresenterInterface? = null

    // team list adapter
    private val teamsAdapter by lazy { TeamsRecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        exitTransition =
            TransitionInflater.from(requireContext())
                .inflateTransition(android.R.transition.no_transition)

        // init presenter
        initPresenter(SearchPresenter(this, Repository()))
        presenter?.loadAllLeagues()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init ui
        initSearchLayout()
        initRecyclerViewList()
    }

    override fun onResume() {
        super.onResume()
        if (presenter?.getAllLeagues().isNullOrEmpty()) {
            presenter?.loadAllLeagues()
        }
        presenter?.getTeamListCache()?.let {
            if (it.isNotEmpty()) teamsAdapter.onNewTeamListLoaded(it)
        }
    }

    override fun getRootView(): View? = rootLayout

    private fun initSearchLayout() {
        et_search.setOnItemClickListener { _, _, _, _ ->
            hideKeyboard(requireContext(), view)
            presenter?.loadTeamsByLeagueName(et_search.editableText.toString())
        }
        et_search.addTextChangedListener {
            teamsAdapter.clearTeamList()
        }
        et_search.setOnEditorActionListener { _, _, _ ->
            presenter?.loadTeamsByLeagueName(et_search.editableText.toString())
            return@setOnEditorActionListener true
        }
        val teamSearchAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.select_dialog_item,
            presenter?.getAllLeagues() ?: emptyList()
        )
        et_search.setAdapter(teamSearchAdapter)
    }

    private fun initRecyclerViewList() {
        // Init recycler view
        rv_teams_list.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
                .apply { recycleChildrenOnDetach = true }
            // set max recycled views list to 8
            recycledViewPool.setMaxRecycledViews(0, 8)
            teamsAdapter.itemClickListener = object : TeamsRecyclerAdapter.OnItemClickListener {
                override fun onClick(view: View, team: Team) {
                    (requireActivity() as MainActivityInterface).showTeamDetailsFragment(
                        view,
                        team
                    )
                }
            }
            adapter = teamsAdapter
        }
    }

    override fun displayError(error: PresenterError) {
        when(error) {
            PresenterError.NO_RESULT_FOUND -> {
                lyt_no_teams_found.visibility = View.VISIBLE
            }
            else -> displaySnackBar()
        }
    }

    override fun displayLoader(isLoadingData: Boolean) {
        if (isLoadingData) {
            lyt_no_teams_found.visibility = View.GONE
            pb_teams_loading.visibility = View.VISIBLE
        } else {
            pb_teams_loading.visibility = View.GONE
        }
    }

    override fun displayTeams(newTeamList: List<Team>) {
        when {
            newTeamList.isNotEmpty() -> {
                // in case new teams loaded
                lyt_no_teams_found.visibility = View.GONE
                teamsAdapter.onNewTeamListLoaded(newTeamList)
            }
            else -> {
                // in case no teams loaded
                lyt_no_teams_found.visibility = View.VISIBLE
            }
        }
    }

    override fun initPresenter(presenter: SearchPresenterInterface) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        super.onDestroy()
    }
}