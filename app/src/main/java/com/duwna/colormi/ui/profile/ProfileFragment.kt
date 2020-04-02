package com.duwna.colormi.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duwna.colormi.R
import com.duwna.colormi.models.User
import com.duwna.colormi.models.fullName
import com.duwna.colormi.models.initials
import com.duwna.colormi.repositories.AuthRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_avatar.isAvatarMode = false

        AuthRepository.firebaseUser ?: findNavController().navigate(R.id.navigation_auth)

        AuthRepository.getUserInfo(
            onComplete = { user -> bindUserData(user) },
            onError = { exception -> onError(exception) }
        )

        btn_sign_out.setOnClickListener {
            AuthRepository.signOut()
            findNavController().navigate(R.id.navigation_auth)
        }

    }

    private fun bindUserData(user: User?) {
        if (user != null) {
            tv_fullName.text = user.fullName
            tv_email.text = user.email
            iv_avatar.setInitials(user.initials)
        } else {
            onError(null)
        }
    }

    private fun onError(exception: Exception?) {
        Snackbar.make(
            container,
            "Не удалось загрузить данные пользователя",
            Snackbar.LENGTH_LONG
        ).show()
        exception?.printStackTrace()
    }
}
