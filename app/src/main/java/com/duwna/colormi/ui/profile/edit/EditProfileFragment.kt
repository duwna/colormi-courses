package com.duwna.colormi.ui.profile.edit

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.models.database.User
import com.duwna.colormi.models.database.initials
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
                et_email.text.toString(),
                et_phone.text.toString(),
                et_first_name.text.toString(),
                et_last_name.text.toString()
            )
            viewModel.saveUser(user)
        }

        iv_avatar.setOnClickListener {
            if (ContextCompat.checkSelfPermission(root, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            viewModel.uploadImage(data?.data)
        }
    }


    override fun subscribeOnState(state: IViewModelState) {
        state as EditProfileState

        swipe_refresh.isRefreshing = state.isLoading
        progress_circular.isVisible = state.isSaving
        btn_save.isVisible = !state.isSaving

        state.user?.let { user ->
            et_email.setText(user.email)
            et_first_name.setText(user.firstName)
            et_last_name.setText(user.lastName)
            et_phone.setText(user.phone)
            iv_avatar.setInitials(user.initials ?: "")

            user.avatarUrl?.let {
                iv_avatar.isAvatarMode = true
                Glide.with(root)
                    .load(it)
                    .into(iv_avatar)
            } ?: run { iv_avatar.isAvatarMode = false }
        }
    }

    companion object {
        const val PICK_IMAGE_CODE = 100
        const val PERMISSION_REQUEST_CODE = 200
    }
}
