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
    private val onItemClicked: (item: CourseItem) -> Unit,
    private val onBookmarkClicked: (item: CourseItem, index: Int) -> Unit
) : ListAdapter<CourseItem, SearchViewHolder>(SearchDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val containerView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_course, parent, false)
        return SearchViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked, onBookmarkClicked)
    }

}

class SearchDiffCallBack : DiffUtil.ItemCallback<CourseItem>() {

    override fun areItemsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean {
        return oldItem.idCourse == newItem.idCourse
    }

    override fun areContentsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: CourseItem, newItem: CourseItem): Any? {
        return if (oldItem.isBookmarked != newItem.isBookmarked || oldItem.isBought != newItem.isBought) Unit
        else super.getChangePayload(oldItem, newItem)
    }
}

class SearchViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(
        item: CourseItem,
        onItemClicked: (item: CourseItem) -> Unit,
        onBookmarkClicked: (item: CourseItem, index: Int) -> Unit
    ) = containerView.run {

        tv_title.text = item.title
        tv_description.text = item.description
        tv_type.text = item.type

        tv_buy.run {
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
                    else -> "${item.price} ₽"
                }
            }

            setOnClickListener { onItemClicked(item) }
        }

        iv_bookmark.run {
            setImageResource(
                if (item.isBookmarked) R.drawable.ic_bookmark_checked
                else R.drawable.ic_bookmark_unchecked
            )
            setOnClickListener { onBookmarkClicked(item, adapterPosition) }
        }
    }


}