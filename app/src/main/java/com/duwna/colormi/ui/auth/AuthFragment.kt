package com.duwna.colormi.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duwna.colormi.MainActivity
import com.duwna.colormi.R
import kotlinx.android.synthetic.main.fragment_authentication.*

class AuthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? MainActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return inflater.inflate(R.layout.fragment_authentication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_enter.setOnClickListener {
            findNavController().navigate(R.id.navigation_enter)
        }
        btn_registration.setOnClickListener {
            findNavController().navigate(R.id.navigation_registration)
        }

        super.onViewCreated(view, savedInstanceState)
    }


}