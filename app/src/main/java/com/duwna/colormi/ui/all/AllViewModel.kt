package com.duwna.colormi.ui.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is all curses Fragment"
    }
    val text: LiveData<String> = _text
}