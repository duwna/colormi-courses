package com.duwna.colormi.ui.search.detail

import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.duwna.colormi.R
import com.duwna.colormi.base.BaseFragment
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.extensions.circularShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_course_detail.*

class CourseDetailFragment : BaseFragment<CourseDetailViewModel>() {

    override val viewModel: CourseDetailViewModel by viewModels {
        CourseDetailViewModelFactory(requireArguments()["idCourse"] as String)
    }

    override val layout: Int = R.layout.fragment_course_detail

    override fun setupViews() {

        root.hideNavView()

        swipe_refresh.setOnRefreshListener { viewModel.loadCourse() }

        btn_buy.setOnClickListener { viewModel.handlePurchase() }
        iv_bookmark.setOnClickListener { viewModel.handleBookmark() }
    }


    override fun subscribeOnState(state: IViewModelState) {
        state as CourseDetailState

        swipe_refresh.isRefreshing = state.isLoading

        when {
            state.courseItem == null -> container.isVisible = false
            !container.isVisible && ViewCompat.isAttachedToWindow(container) -> container.circularShow()
            else -> container.isVisible = true
        }

        state.courseItem?.run {

            tv_answer.text = description
            tv_question.text = title
            tv_type.text = type
            tv_rating.text = rating

            if (imgUrl.isNotBlank()) Picasso.get()
                .load(imgUrl)
                .into(iv_img)

            btn_buy.run {
                if (isBought) {
//                    isClickable = false
                    text = "Куплено"
                } else {
//                    isClickable = true
                    text = when (price) {
                        0 -> "Бесплатно"
                        else -> "Купить курс: $price ₽"
                    }
                }

                iv_bookmark.run {
                    setImageResource(
                        if (isBookmarked) R.drawable.ic_bookmark_checked
                        else R.drawable.ic_bookmark_unchecked
                    )
                }
            }
        }
    }

}
