package com.duwna.colormi.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duwna.colormi.R
import com.duwna.colormi.repositories.AuthRepository
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

        AuthRepository.user ?: findNavController().navigate(R.id.navigation_auth)

        btn_sign_out.setOnClickListener {
            AuthRepository.signOut()
            findNavController().navigate(R.id.navigation_auth)
        }

        tv_email.text = AuthRepository.user?.email
    }
}
