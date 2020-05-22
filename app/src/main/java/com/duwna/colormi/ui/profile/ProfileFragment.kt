package com.duwna.colormi.ui.profile

import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.extensions.circularShow
import com.duwna.colormi.models.database.fullName
import com.duwna.colormi.models.database.initials
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override val layout: Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    override fun setupViews() {

        btn_edit_profile.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_edit)
        }

        btn_about.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_about)
        }

        btn_faq.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_faq)
        }

        btn_settings.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_settings)
        }

        btn_sign_out.setOnClickListener {
            viewModel.singOut()
        }

        swipe_refresh.setOnRefreshListener {
            viewModel.loadUser()
        }
    }

    override fun subscribeOnState(state: IViewModelState) {
        state as ProfileState

        swipe_refresh.isRefreshing = state.isLoading

        when {
            state.user == null -> card_profile_data.visibility = View.INVISIBLE
            !card_profile_data.isVisible && ViewCompat.isAttachedToWindow(card_profile_data) ->
                card_profile_data.circularShow()
            else -> card_profile_data.isVisible = true
        }

        state.user?.let { user ->

            tv_fullName.text = user.fullName
            tv_email.text = user.email
            iv_avatar.setInitials(user.initials ?: "")

            Log.e("TAG", user.toString())
            user.avatarUrl?.let {
                iv_avatar.isAvatarMode = true
                Picasso.get()
                    .load(it)
                    .into(iv_avatar)
            } ?: run { iv_avatar.isAvatarMode = false }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadUser()
    }

    override fun onResume() {
        super.onResume()
        root.showNavView()
    }
}
