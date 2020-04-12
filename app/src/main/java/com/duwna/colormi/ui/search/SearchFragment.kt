package com.duwna.colormi.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.duwna.colormi.R
import com.duwna.colormi.repositories.generateCourseItem
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private val searchAdapter = SearchAdapter(
        {

        },
        {

        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_courses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }

        val list = MutableList((10..50).random()) { generateCourseItem() }
        searchAdapter.submitList(list)
    }

}