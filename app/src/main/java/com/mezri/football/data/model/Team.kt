package com.mezri.football.data.model

import android.os.Parcel
import android.os.Parcelable
import com.mezri.football.data.network.dto.TeamDTO

private const val UNKNOWN_VALUE = "unknow"

data class Team(
    val id: String,
    val name: String,
    val logoURL: String,
    val bannerURL: String,
    val country: String,
    val league: String,
    val description: String
) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: UNKNOWN_VALUE,
        parcel.readString() ?: UNKNOWN_VALUE,
        parcel.readString() ?: UNKNOWN_VALUE,
        parcel.readString() ?: UNKNOWN_VALUE,
        parcel.readString() ?: UNKNOWN_VALUE,
        parcel.readString() ?: UNKNOWN_VALUE,
        parcel.readString() ?: UNKNOWN_VALUE
    )

    constructor(teamDTO: TeamDTO) : this(
        teamDTO.idTeam ?: UNKNOWN_VALUE,
        teamDTO.strTeam ?: UNKNOWN_VALUE,
        teamDTO.strTeamBadge ?: UNKNOWN_VALUE,
        teamDTO.strTeamBanner ?: UNKNOWN_VALUE,
        teamDTO.strCountry ?: UNKNOWN_VALUE,
        teamDTO.strLeague ?: UNKNOWN_VALUE,
        teamDTO.strDescriptionFR ?: UNKNOWN_VALUE
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(logoURL)
        parcel.writeString(bannerURL)
        parcel.writeString(country)
        parcel.writeString(league)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Team> {
        override fun createFromParcel(parcel: Parcel): Team {
            return Team(parcel)
        }

        override fun newArray(size: Int): Array<Team?> {
            return arrayOfNulls(size)
        }
    }
}