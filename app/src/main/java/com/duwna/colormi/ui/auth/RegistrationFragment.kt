package com.duwna.colormi.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duwna.colormi.R
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_registration.setOnClickListener { onRegisterClick() }
    }

    private fun onRegisterClick() {
        if (!isInputValid()) return
        AuthRepository.registerUser(
            "name",
            et_email.text.toString(),
            et_password.text.toString(),
            { findNavController().navigate(R.id.navigation_profile) },
            { exception -> onError(exception) }
        )
    }

    private fun onError(exception: Exception?) {
        Snackbar.make(
            container,
            exception?.localizedMessage as CharSequence,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun isInputValid(): Boolean {
        var message: String? = null
        when {
            et_name.text.isBlank() -> message = "Имя не должно быть пустым."
            et_email.text.isBlank() -> message = "Email не должен быть пустым."
            et_password.text.length < 6 -> message = "Пароль должен содержать минимум 6 символов."
            et_password.text.toString() != et_re_password.text.toString() ->
                message = "Пароли не совпадают."
        }
        return if (message != null) {
            Snackbar.make(container, message, Snackbar.LENGTH_LONG).show()
            false
        } else {
            true
        }
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
}

