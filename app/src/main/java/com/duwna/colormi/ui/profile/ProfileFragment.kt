package com.duwna.colormi.ui.profile

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.models.database.fullName
import com.duwna.colormi.models.database.initials
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

        swipe_refresh.isRefreshing = state.isLoading

        state.user?.let { user ->
            tv_fullName.text = user.fullName
            tv_email.text = user.email
            iv_avatar.setInitials(user.initials ?: "")

            Log.e("TAG", user.toString())
            user.avatarUrl?.let {
                iv_avatar.isAvatarMode = true
                Glide.with(root)
                    .load(it)
                    .into(iv_avatar)
            } ?: run { iv_avatar.isAvatarMode = false }
        }
    }
}
