package com.duwna.colormi.ui.profile

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.models.fullName
import com.duwna.colormi.models.initials
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override val layout: Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    override fun setupViews() {

        btn_edit_profile.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_edit)
        }

        btn_sign_out.setOnClickListener {
            viewModel.singOut()
            findNavController().navigate(R.id.action_profile_to_auth)
        }

        swipe_refresh.setOnRefreshListener {
            viewModel.loadUser()
        }
    }

    override fun subscribeOnState(state: IViewModelState) {
        state as ProfileState

        Log.e("TAF", state.user.toString())
        swipe_refresh.isRefreshing = state.isLoading

        tv_fullName.text = state.user?.fullName
        tv_email.text = state.user?.email
        iv_avatar.setInitials(state.user?.initials ?: "")
    }
}
