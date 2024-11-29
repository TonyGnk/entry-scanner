package com.tonygnk.entry_scanner.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonygnk.entry_scanner.core.common.utils.ConnectivityUtils
import com.tonygnk.entry_scanner.data.repositories.AdminDaoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class AppViewModel(
    private val repository: AdminDaoRepository,
    connectivityObserver: ConnectivityUtils
) : ViewModel() {

    val teamsFlow = repository.getTeams().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    val isConnected = connectivityObserver
        .isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            true
        )


    fun setArrived(teamId: String, index: Int, value: Boolean) {
        viewModelScope.launch {
            repository.updateParticipantArrived(teamId, index, value)
        }
    }
}
