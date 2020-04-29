package com.duwna.colormi.ui.search

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment<SearchViewModel>() {

    override val layout: Int = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModels()

    private val searchAdapter = SearchAdapter(
        onBuyClicked = {

        },
        onBookmarkClicked = { courseItem, index ->
            viewModel.handleBookmark(courseItem, index)
        }
    )

    override fun setupViews() {

        rv_courses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }

        swipe_refresh.setOnRefreshListener {
            viewModel.loadCourses()
        }
    }

    override fun subscribeOnState(state: IViewModelState) {
        state as SearchState

        swipe_refresh.isRefreshing = state.isLoading
        searchAdapter.submitList(state.coursesList)
    }

}