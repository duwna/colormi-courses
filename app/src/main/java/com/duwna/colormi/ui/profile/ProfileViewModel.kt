package com.duwna.colormi.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duwna.colormi.models.User
import com.duwna.colormi.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    val user = MutableLiveData<User>()
    val error = MutableLiveData<Throwable>()

    init {
//        AuthRepository.getUserInfo(
//            onComplete = { user.value = it }
//        )
//        AuthRepository.getUserInfoRealtime (
//            onComplete = { user.value = it }
//        )
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                user.postValue(AuthRepository.getSuspendUserInfo(false))
            } catch (e: Throwable) {
                error.postValue(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("TAG", "onCleared")
    }
}