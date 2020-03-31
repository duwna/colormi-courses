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
import com.duwna.colormi.repositories.AuthRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AuthRepository.firebaseUser ?: findNavController().navigate(R.id.navigation_auth)

        AuthRepository.getUserInfo(
            onComplete = { user -> bindUserData(user) },
            onError = {
                Snackbar.make(
                    container,
                    "Не удалось загрузить данные пользователя",
                    Snackbar.LENGTH_LONG
                ).show()
                it?.printStackTrace()
            }
        )

        btn_sign_out.setOnClickListener {
            AuthRepository.signOut()
            findNavController().navigate(R.id.navigation_auth)
        }

    }

    private fun bindUserData(user: User?) {
        tv_name.text = user?.firstName
        tv_email.text = user?.email
    }
}
