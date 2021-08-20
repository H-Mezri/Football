package com.mezri.football.data.network.dto

interface DTOMapper<I, O> {
    fun map(input: I): O
}