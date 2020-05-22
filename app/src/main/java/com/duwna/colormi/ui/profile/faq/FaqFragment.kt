package com.duwna.colormi.ui.profile.faq

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import kotlinx.android.synthetic.main.fragment_faq.*

class FaqFragment : BaseFragment<NewsViewModel>() {

    override val viewModel: NewsViewModel by viewModels()
    override val layout: Int = R.layout.fragment_faq

    private val faqAdapter = FaqAdapter()

    override fun setupViews() {
        rv_faq.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = faqAdapter
        }

        swipe_refresh.setOnRefreshListener {
            viewModel.loadFaq()
        }
    }

    override fun subscribeOnState(state: IViewModelState) {
        state as NewsState
        swipe_refresh.isRefreshing = state.isLoading
        faqAdapter.submitList(state.faqList)
    }

}
