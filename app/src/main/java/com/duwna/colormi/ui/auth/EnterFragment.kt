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
import com.duwna.colormi.repositories.AuthRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_enter.*

class EnterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_enter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_enter.setOnClickListener { onEnterClick() }
    }

    private fun onEnterClick() {
        if (!isInputValid()) return
        setLoadingState(true)
        AuthRepository.authUser(
            et_email.text.toString(),
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isInputValid(): Boolean {
        var message: String? = null
        when {
            et_email.text.isBlank() -> message = "Email не должен быть пустым."
            !et_email.text.matches("[^@]+@[^.]+\\..+".toRegex()) ->
                message = "Email не является валидным."
            et_password.text.isBlank() -> message = "Пароль не должен быть пустым."
        }
        return message?.let {
            Snackbar.make(container, it, Snackbar.LENGTH_LONG).show()
            false
        } ?: true
    }

    private fun setLoadingState(isLoading: Boolean) {
        btn_enter.isVisible = !isLoading
        progress_circular.isVisible = isLoading
    }

}