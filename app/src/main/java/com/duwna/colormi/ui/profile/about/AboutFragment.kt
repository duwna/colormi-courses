package com.duwna.colormi.ui.profile.about

import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.extensions.circularShow
import io.noties.markwon.Markwon
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : BaseFragment<AboutViewModel>() {

    override val viewModel: AboutViewModel by viewModels()
    override val layout: Int = R.layout.fragment_about

    private lateinit var markwon: Markwon

    override fun setupViews() {
        markwon = Markwon.create(requireContext())
        swipe_refresh.setOnRefreshListener { viewModel.loadAboutText() }
    }

    override fun subscribeOnState(state: IViewModelState) {
        state as AboutState

        swipe_refresh.isRefreshing = state.isLoading

        when {
            state.text == null -> container.isVisible = false
            !container.isVisible && ViewCompat.isAttachedToWindow(container) -> container.circularShow()
            else -> container.isVisible = true
        }

        state.text?.let { markwon.setMarkdown(tv_answer, it) }
    }

}