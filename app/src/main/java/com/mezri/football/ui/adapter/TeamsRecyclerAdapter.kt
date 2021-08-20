package com.mezri.football.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.mezri.football.data.model.Team
import com.mezri.football.databinding.TeamItemBinding
import com.mezri.football.utils.glide.load
import kotlinx.android.synthetic.main.team_item.view.*

class TeamsRecyclerAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onClick(view: View, team: Team)
    }

    var itemClickListener: OnItemClickListener? = null

    private val teamList = mutableListOf<Team>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val albumItemBinding = TeamItemBinding.inflate(layoutInflater, parent, false)
        return AlbumItemViewHolder(albumItemBinding)
    }

    override fun onBindViewHolder(holderItem: RecyclerView.ViewHolder, position: Int) {
        (holderItem as AlbumItemViewHolder).bind(teamList[position] as Team)
    }

    inner class AlbumItemViewHolder(private val teamItemBinding: TeamItemBinding) :
        RecyclerView.ViewHolder(teamItemBinding.root) {
        fun bind(team: Team) {
            teamItemBinding.team = team
            itemView.pb_image_loading.visibility = View.VISIBLE
            // load album cover
            itemView.imgAlbumCover.load(team.logoURL) {
                itemView.pb_image_loading.visibility = View.GONE
            }
            // init image view transition name
            ViewCompat.setTransitionName(itemView.imgAlbumCover, team.id)

            // set view on click listener
            itemView.setOnClickListener {
                itemClickListener?.onClick(itemView.imgAlbumCover, team)
            }
        }
    }

    override fun getItemCount(): Int = teamList.size

    override fun getItemId(position: Int): Long {
        return teamList[position].hashCode().toLong()
    }

    /**
     * Update list when request result received
     */
    fun onNewTeamListLoaded(newAlbumsList: List<Team>) {
        teamList.clear()
        if (newAlbumsList.isNotEmpty()) {
            teamList.addAll(newAlbumsList)
        }
        notifyDataSetChanged()
    }

    /**
     * Clear team list
     */
    fun clearTeamList() {
        if (teamList.isNotEmpty()) {
            teamList.clear()
            notifyDataSetChanged()
        }
    }
}