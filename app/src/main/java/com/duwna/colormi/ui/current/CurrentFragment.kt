package com.duwna.colormi.ui.current

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import kotlinx.android.synthetic.main.fragment_current.*


class CurrentFragment : BaseFragment<CurrentViewModel>() {
    override val viewModel: CurrentViewModel by viewModels()
    override val layout: Int = R.layout.fragment_current

    private val currentAdapter = CurrentAdapter(
        onWatchClicked = {
            openIntent(it.videoUrl)
        },
        onDownloadClicked = {
            openIntent(it.documentUrl)
        }
    )

    override fun setupViews() {
        rv_courses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currentAdapter
        }

        swipe_refresh.setOnRefreshListener {
            viewModel.loadCourses()
        }
    }

    override fun subscribeOnState(state: IViewModelState) {
        state as CurrentCoursesState
        swipe_refresh.isRefreshing = state.isLoading
        currentAdapter.submitList(state.coursesList)
    }

    private fun openIntent(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onResume() {
        super.onResume()
        root.showNavView()
    }

}