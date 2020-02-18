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
import kotlinx.android.synthetic.main.fragment_registration_second.*

class RegistrationFragmentSecond : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_registration_second, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        btn_registration.setOnClickListener { onRegisterClick() }
        super.onActivityCreated(savedInstanceState)
    }

    private fun onRegisterClick() {

        if (!isInputValid()) return
        val user = User(
            null,
            et_email.text.toString(),
            arguments!!.getString("firstName")!!,
            arguments!!.getString("phone")!!,
            arguments!!.getString("lastName")!!
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
        findNavController().navigate(R.id.navigation_profile)
        setLoadingState(false)
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

    private fun setLoadingState(isLoading: Boolean) {
        card_view.isVisible = !isLoading
        progress_circular.isVisible = isLoading
    }
}

