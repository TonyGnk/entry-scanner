package com.tonygnk.entry_scanner.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tonygnk.entry_scanner.data.model.Participant
import com.tonygnk.entry_scanner.data.model.Team
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface AdminDaoRepository {
    fun getTeams(): Flow<List<Team>>
    suspend fun updateParticipantArrived(teamId: String, participantIndex: Int, value: Boolean)
}

class AdminDaoRepositoryImpl(
    fireStore: FirebaseFirestore
) : AdminDaoRepository {

    private val teamsCollection = fireStore.collection("participants")

    override suspend fun updateParticipantArrived(
        teamId: String,
        participantIndex: Int,
        value: Boolean
    ) {
        try {
            val documentSnapshot = teamsCollection.document(teamId).get().await()

            val membersList = documentSnapshot.get("members") as? List<Map<String, Any>>
            val members: List<Participant?> = membersList?.map { memberData ->
                val email = memberData["email"] as? String
                val hasArrived = memberData["hasArrived"] as? Boolean
                val name = memberData["name"] as? String
                val school = memberData["school"] as? String
                val subject = memberData["subject"] as? String
                if (
                    email != null && hasArrived != null && name != null && school != null && subject != null
                ) {
                    Participant(
                        email = email,
                        hasArrived = hasArrived,
                        name = name,
                        school = school,
                        subject = subject
                    )
                } else {
                    null
                }
            } ?: emptyList()

            val previousTeam = Team(
                id = documentSnapshot.id,
                autoTeam = documentSnapshot.getBoolean("autoTeam")!!,
                category = documentSnapshot.getString("category")!!,
                createdAt = documentSnapshot.getTimestamp("createdAt")!!,
                privacyPolicy = documentSnapshot.getBoolean("privacyPolicy")!!,
                teamName = documentSnapshot.getString("teamName")!!,
                members = members.filterNotNull()
            )

            val newTeam = previousTeam.copy(
                members = previousTeam.members.mapIndexed { index, participant ->
                    if (index == participantIndex) {
                        participant.copy(hasArrived = value)
                    } else {
                        participant
                    }
                }
            )

            teamsCollection.document(teamId).set(newTeam).await()
        } catch (e: Exception) {
            Log.e("UpdateParticipant", "Error updating participant", e)
        }
    }


    override fun getTeams(): Flow<List<Team>> = callbackFlow {

        val listener = teamsCollection.orderBy("teamName", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) {
                    close(e)
                } else {
                    val team = snapshot.documents.mapNotNull { document ->
                        Log.d("AdminDaoRepositoryImpl", "getTeams: document = $document")
                        val teamId = document.id
                        val autoTeam = document.getBoolean("autoTeam")
                        val category = document.getString("category")
                        val createdAt = document.getTimestamp("createdAt")
                        val privacyPolicy = document.getBoolean("privacyPolicy")
                        val teamName = document.getString("teamName")
                        val membersList = document.get("members") as? List<Map<String, Any>>
                        val members: List<Participant?> = membersList?.map { memberData ->
                            val id = memberData.keys.first()
                            val email = memberData["email"] as? String
                            val hasArrived = memberData["hasArrived"] as? Boolean
                            val name = memberData["name"] as? String
                            val school = memberData["school"] as? String
                            val subject = memberData["subject"] as? String
                            if (
                                email != null && hasArrived != null && name != null && school != null && subject != null
                            ) {
                                Participant(
                                    email = email,
                                    hasArrived = hasArrived,
                                    name = name,
                                    school = school,
                                    subject = subject
                                )
                            } else {
                                null
                            }
                        } ?: emptyList()
                        if (
                            autoTeam != null && category != null && createdAt != null && privacyPolicy != null && teamName != null
                        ) {
                            Team(
                                id = teamId,
                                autoTeam = autoTeam,
                                category = category,
                                createdAt = createdAt,
                                privacyPolicy = privacyPolicy,
                                teamName = teamName,
                                members = members.filterNotNull()
                            )
                        } else {
                            null
                        }
                    }.map { it }
                    trySend(team).isSuccess
                }
            }
        awaitClose { listener.remove() }
    }
}
