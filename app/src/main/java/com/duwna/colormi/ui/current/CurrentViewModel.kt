package com.duwna.colormi.ui.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is current curses Fragment"
    }
    val text: LiveData<String> = _text
}