package com.duwna.colormi.ui.profile.edit

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.models.User
import com.duwna.colormi.models.initials
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : BaseFragment<EditProfileViewModel>() {

    override val viewModel: EditProfileViewModel by viewModels()
    override val layout = R.layout.fragment_edit_profile

    override fun setupViews() {

        swipe_refresh.setOnRefreshListener {
            viewModel.loadUser()
        }

        btn_save.setOnClickListener {
            val user = User(
                null,
                et_email.text.toString(),
                et_phone.text.toString(),
                et_first_name.text.toString(),
                et_last_name.text.toString(),
                ""
            )
            viewModel.saveUser(user)
        }


    }

    override fun subscribeOnState(state: IViewModelState) {
        state as EditProfileState

        Log.e("TAG", state.toString())

        swipe_refresh.isRefreshing = state.isLoading
        progress_circular.isVisible = state.isSaving
        btn_save.isVisible = !state.isSaving

        et_email.setText(state.user?.email)
        et_first_name.setText(state.user?.firstName)
        et_last_name.setText(state.user?.lastName)
        et_phone.setText(state.user?.phone)
        iv_avatar.setInitials(state.user?.initials ?: "")
    }

}
