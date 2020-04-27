package com.duwna.colormi.ui.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.Notify
import com.duwna.colormi.models.User
import com.duwna.colormi.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : BaseViewModel<ProfileState>(ProfileState()) {

    private val repository = UserRepository()

    init {
        loadUser()
    }

    fun loadUser() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.Unconfined) {
            try {
                val result = repository.getUserInfo()
                updateState { it.copy(user = result.first, isLoading = false) }
                Log.e("TAG", currentState.toString())
                if (result.second) notify(Notify.InternetError())
            } catch (e: Throwable) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.Error())
                e.printStackTrace()
            }
        }
    }

    fun singOut() {
        repository.signOut()
    }

}

data class ProfileState(
    val user: User? = null,
    val isLoading: Boolean = false
) : IViewModelState