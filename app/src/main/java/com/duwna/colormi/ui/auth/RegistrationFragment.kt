package com.duwna.colormi.ui.auth

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.models.database.User
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : BaseFragment<AuthViewModel>() {

    override val viewModel: AuthViewModel by viewModels()
    override val layout: Int = R.layout.fragment_registration

    override fun setupViews() {

        btn_register.setOnClickListener {
            val user = User(
                et_email.text.toString(),
                et_phone.text.toString(),
                et_first_name.text.toString(),
                et_last_name.text.toString()
            )
            viewModel.register(
                user,
                et_password.text.toString(),
                et_re_password.text.toString()
            )
        }

        et_first_name.setText("John")
        et_last_name.setText("Doe")
        et_phone.setText("+71111111111")
        et_email.setText("john_doe@mail.ru")
        et_password.setText("password")
        et_re_password.setText("password")
    }

    override fun subscribeOnState(state: IViewModelState) {
        state as AuthState

        btn_register.isVisible = !state.isLoading
        progress_circular.isVisible = state.isLoading

        if (state.success != null) findNavController().popBackStack(R.id.navigation_auth, true)

    }
}
