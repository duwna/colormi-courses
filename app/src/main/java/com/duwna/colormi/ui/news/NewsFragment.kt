package com.duwna.colormi.ui.news

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseFragment<NewsViewModel>() {

    override val viewModel: NewsViewModel by viewModels()
    override val layout: Int = R.layout.fragment_news

    private val newsAdapter = NewsAdapter()

    override fun setupViews() {
        rv_news.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        swipe_refresh.setOnRefreshListener {
            viewModel.loadNews()
        }
    }

    override fun subscribeOnState(state: IViewModelState) {
        state as NewsState
        swipe_refresh.isRefreshing = state.isLoading
        newsAdapter.submitList(state.newsList)
    }

}
