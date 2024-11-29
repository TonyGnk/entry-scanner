package com.tonygnk.entry_scanner.data.model

import com.google.firebase.Timestamp


data class Participant(
    val email: String,
    val name: String,
    val hasArrived: Boolean,
    val school: String,
    val subject: String,
)

data class Team(
    val id: String,
    val teamName: String,
    val category: String,
    val members: List<Participant>,
    val privacyPolicy: Boolean,
    val createdAt: Timestamp,
    val autoTeam: Boolean,
)
