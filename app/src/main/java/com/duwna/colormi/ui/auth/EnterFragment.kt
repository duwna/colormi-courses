package com.duwna.colormi.ui.auth

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import kotlinx.android.synthetic.main.fragment_enter.*

class EnterFragment : BaseFragment<AuthViewModel>() {

    override val viewModel: AuthViewModel by viewModels()
    override val layout: Int = R.layout.fragment_enter

    override fun setupViews() {
        btn_enter.setOnClickListener {
            viewModel.enter(et_email.text.toString(), et_password.text.toString())
        }
    }

    override fun subscribeOnState(state: IViewModelState) {
        state as AuthState

        btn_enter.isVisible = !state.isLoading
        progress_circular.isVisible = state.isLoading

        if (state.success != null) findNavController().navigate(R.id.navigation_profile)
    }
}