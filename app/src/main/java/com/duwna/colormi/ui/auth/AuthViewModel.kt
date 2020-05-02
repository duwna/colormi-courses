package com.duwna.colormi.ui.auth

import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.Notify
import com.duwna.colormi.models.database.User
import com.duwna.colormi.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : BaseViewModel<AuthState>(AuthState()) {

    private val repository = UserRepository()

    fun enter(email: String, password: String) {

        if (!validateEnter(email, password)) return
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.authUser(email, password)
                updateState { it.copy(isLoading = false, success = Unit) }
            } catch (e: Exception) {
                if (e.localizedMessage != null)
                    notify(Notify.TextMessage(e.localizedMessage!!))
                else notify(Notify.Error())
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    private fun validateEnter(email: String, password: String): Boolean {
        when {
            email.isBlank() -> {
                notify(Notify.TextMessage("Email не должен быть пустым"))
                return false
            }
            !email.matches("[^@]+@[^.]+\\..+".toRegex()) -> {
                notify(Notify.TextMessage("Email не является валидным."))
                return false
            }
            password.isBlank() -> {
                notify(Notify.TextMessage("Пароль не должен быть пустым"))
                return false
            }
        }
        return true
    }

    fun register(user: User, password: String, rePassword: String) {

        if (!validateRegister(user, password, rePassword)) return
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.registerUser(user, password)
                updateState { it.copy(isLoading = false, success = Unit) }
            } catch (e: Exception) {
                if (e.localizedMessage != null)
                    notify(Notify.TextMessage(e.localizedMessage!!))
                else notify(Notify.Error())
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    private fun validateRegister(user: User, password: String, rePassword: String): Boolean {
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
            password.length < 6 -> {
                notify(Notify.TextMessage("Пароль должен содержать минимум 6 символов."))
                return false
            }
            password != rePassword -> {
                notify(Notify.TextMessage("Пароли не совпадают."))
                return false
            }
        }
        return true
    }


}

data class AuthState(
    val isLoading: Boolean = false,
    val success: Unit? = null
) : IViewModelState