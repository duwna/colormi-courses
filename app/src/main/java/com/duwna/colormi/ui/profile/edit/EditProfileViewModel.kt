package com.duwna.colormi.ui.profile.edit

import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.Notify
import com.duwna.colormi.models.User
import com.duwna.colormi.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel : BaseViewModel<EditProfileState>(
    EditProfileState()
) {

    private val repository = UserRepository()

    init {
        loadUser()
    }

    fun loadUser() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getUserInfo()
                updateState { it.copy(user = result.first, isLoading = false, isSaving = false) }
                if (result.second) notify(Notify.InternetError())
            } catch (e: Throwable) {
                updateState { it.copy(isLoading = false, isSaving = false) }
                notify(Notify.Error())
            }
        }
    }

    fun saveUser(user: User) {
        if (!validate(user)) return
        viewModelScope.launch {
            updateState { it.copy(isSaving = true) }
            try {
                repository.updateUser(user)
                notify(Notify.TextMessage("Данные сохранены"))
            } catch (e: Throwable) {
                notify(Notify.Error())
            }
            loadUser()
        }
    }

    private fun validate(user: User): Boolean {
        when {
            user.firstName.isBlank() -> {
                notify(Notify.TextMessage("Имя не должено быть пустым."))
                return false
            }
            user.lastName.isBlank() -> {
                notify(Notify.TextMessage("Фамилия не должена быть пустым."))
                return false
            }
            !user.phone.startsWith('+') || user.phone.length != 12 -> {
                notify(Notify.TextMessage("Телефон должен начинаться с '+' и содержать 11 цифр."))
                return false
            }
            user.email.isBlank() -> {
                notify(Notify.TextMessage("Email не должен быть пустым."))
                return false
            }
            !user.email.matches("[^@]+@[^.]+\\..+".toRegex()) -> {
                notify(Notify.TextMessage("Email не является валидным."))
                return false
            }
        }
        return true
    }
}

data class EditProfileState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false
) : IViewModelState