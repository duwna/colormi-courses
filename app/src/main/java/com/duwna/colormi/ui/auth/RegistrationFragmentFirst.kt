package com.duwna.colormi.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duwna.colormi.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_registration_first.*

class RegistrationFragmentFirst : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_registration_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_next.setOnClickListener { onNextClick() }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onNextClick() {
        if (!isInputValid()) return
        val bundle = bundleOf(
            "firstName" to et_first_name.text.toString(),
            "lastName" to et_last_name.text.toString(),
            "phone" to et_phone.text.toString()
        )
        findNavController().navigate(
            R.id.navigation_registration_second,
            bundle
        )
    }

    private fun isInputValid(): Boolean {
        var message: String? = null
        when {
            et_first_name.text.isBlank() -> message = "Имя не должено быть пустым."
            et_last_name.text.isBlank() -> message = "Фамилия не должена быть пустым."
            !(et_phone.text.startsWith('+') && et_phone.text.length == 12) ->
                message = "Телефон должен начинаться с '+' и содержать 11 цифр."
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
