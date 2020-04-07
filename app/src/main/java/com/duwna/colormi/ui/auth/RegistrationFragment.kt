package com.duwna.colormi.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duwna.colormi.R
import com.duwna.colormi.models.User
import com.duwna.colormi.repositories.AuthRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_register.setOnClickListener { onRegisterClick() }
        super.onViewCreated(view, savedInstanceState)

        et_first_name.setText("Firstname")
        et_last_name.setText("Lastname")
        et_phone.setText("+71111111111")
        et_email.setText("email@mail.ru")
        et_password.setText("password")
        et_re_password.setText("password")
    }

    private fun onRegisterClick() {
        if (!isInputValid()) return
        val user = User(
            null,
            et_email.text.toString(),
            et_phone.text.toString(),
            et_first_name.text.toString(),
            et_last_name.text.toString()
        )
        setLoadingState(true)
        AuthRepository.registerUser(
            user,
            et_password.text.toString(),
            { onSuccess() },
            { exception -> onError(exception) }
        )
    }

    private fun onSuccess() {
        setLoadingState(false)
        findNavController().navigate(R.id.navigation_profile)
    }

    private fun onError(exception: Exception?) = try {
        Snackbar.make(
            container,
            exception?.localizedMessage as CharSequence,
            Snackbar.LENGTH_LONG
        ).show()
        setLoadingState(false)
    } catch (e: Exception) {
    }

    private fun isInputValid(): Boolean {
        var message: String? = null
        when {
            et_first_name.text.isBlank() ->
                message = "Имя не должено быть пустым."
            et_last_name.text.isBlank() ->
                message = "Фамилия не должена быть пустым."
            !et_phone.text.startsWith('+') || et_phone.text.length != 12 ->
                message = "Телефон должен начинаться с '+' и содержать 11 цифр."
            et_email.text.isBlank() ->
                message = "Email не должен быть пустым."
            !et_email.text.matches("[^@]+@[^.]+\\..+".toRegex()) ->
                message = "Email не является валидным."
            et_password.text.length < 6 ->
                message = "Пароль должен содержать минимум 6 символов."
            et_password.text.toString() != et_re_password.text.toString() ->
                message = "Пароли не совпадают."
        }
        return message?.let {
            Snackbar.make(container, it, Snackbar.LENGTH_LONG).show()
            false
        } ?: true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        btn_register.isVisible = !isLoading
        progress_circular.isVisible = isLoading
    }
}
