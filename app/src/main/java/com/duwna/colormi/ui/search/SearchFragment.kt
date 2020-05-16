package com.duwna.colormi.ui.search

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

        setHasOptionsMenu(true)

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
        searchAdapter.submitList(state.showCurses())

        Log.e("showCurses", state.showCurses().map { it.isBookmarked }.toString())
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter_bookmark -> {
                viewModel.handleOnlyBookmarked()
                root.invalidateOptionsMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem = menu.findItem(R.id.action_filter_bookmark)
        if (viewModel.currentState.isOnlyBookmarked) {
            menuItem.setIcon(R.drawable.ic_bookmark_checked_menu)
        } else {
            menuItem.setIcon(R.drawable.ic_bookmark_unchecked_menu)
        }
    }

}