package com.duwna.colormi.ui.profile

import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.NoAuthException
import com.duwna.colormi.base.Notify
import com.duwna.colormi.models.database.User
import com.duwna.colormi.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : BaseViewModel<ProfileState>(ProfileState()) {

    private val repository = UserRepository()

    fun loadUser() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getUserInfo()
                updateState { it.copy(user = result.first, isLoading = false) }
                if (result.second) notify(Notify.NoInternetConnection())
            } catch (e: NoAuthException) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.NoAuthentication())
            } catch (t: Throwable) {
                notify(Notify.NoInternetConnection())
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    fun singOut() {
        repository.signOut()
        notify(Notify.TextMessage("Вы вышли из аккаунта"))
    }

}

data class ProfileState(
    val user: User? = null,
    val isLoading: Boolean = false
) : IViewModelState