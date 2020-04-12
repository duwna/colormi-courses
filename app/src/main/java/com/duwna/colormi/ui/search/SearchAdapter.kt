package com.duwna.colormi.ui.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duwna.colormi.R
import com.duwna.colormi.models.CourseItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_search_course.view.*

class SearchAdapter(
    private val onBuyClicked: (item: CourseItem) -> Unit,
    private val onBookmarkClicked: (item: CourseItem) -> Unit
) : ListAdapter<CourseItem, SearchViewHolder>(SearchDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val containerView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_course, parent, false)
        return SearchViewHolder(containerView, onBuyClicked, onBookmarkClicked)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SearchDiffCallBack : DiffUtil.ItemCallback<CourseItem>() {

    override fun areItemsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean {
        return oldItem == newItem
    }

}

class SearchViewHolder(
    override val containerView: View,
    private val onBuyClicked: (item: CourseItem) -> Unit,
    private val onBookmarkClicked: (item: CourseItem) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: CourseItem) = containerView.run {
        tv_title.text = item.title
        tv_description.text = item.description
        tv_type.text = item.type

        btn_buy.run {
            if (item.isBought) {
                background = context.getDrawable(R.drawable.button_round_corners_white)
                setTextColor(Color.BLACK)
                isClickable = false
                text = "Куплено"
            } else {
                background = context.getDrawable(R.drawable.button_round_corners_black)
                setTextColor(Color.WHITE)
                isClickable = true
                text = when (item.price) {
                    0 -> "Бесплатно"
                    else -> "Купить"
                }
            }

            setOnClickListener { onBuyClicked(item) }
        }

        iv_bookmark.run {
            setImageResource(
                if (item.isBookmarked) R.drawable.ic_bookmark_checked
                else R.drawable.ic_bookmark_unchecked
            )
            setOnClickListener { onBookmarkClicked(item) }
        }

    }

}