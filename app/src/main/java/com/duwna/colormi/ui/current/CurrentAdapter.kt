package com.duwna.colormi.ui.current

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duwna.colormi.R
import com.duwna.colormi.extensions.daysLeftToString
import com.duwna.colormi.models.CurrentCourseItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_current_course.view.*

class CurrentAdapter(
    private val onWatchClicked: (item: CurrentCourseItem) -> Unit,
    private val onDownloadClicked: (item: CurrentCourseItem) -> Unit
) : ListAdapter<CurrentCourseItem, CurrentViewHolder>(SearchDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentViewHolder {
        val containerView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_current_course, parent, false)
        return CurrentViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: CurrentViewHolder, position: Int) {
        holder.bind(getItem(position), onWatchClicked, onDownloadClicked)
    }

}

class SearchDiffCallBack : DiffUtil.ItemCallback<CurrentCourseItem>() {

    override fun areItemsTheSame(oldItem: CurrentCourseItem, newItem: CurrentCourseItem): Boolean {
        return oldItem.idCourse == newItem.idCourse
    }

    override fun areContentsTheSame(oldItem: CurrentCourseItem, newItem: CurrentCourseItem): Boolean {
        return oldItem == newItem
    }
}

class CurrentViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(
        item: CurrentCourseItem,
        onWatchClicked: (item: CurrentCourseItem) -> Unit,
        onDownloadClicked: (item: CurrentCourseItem) -> Unit
    ) = containerView.run {

        tv_title.text = item.title
        tv_text.text = item.description
        tv_type.text = item.type
        tv_date.text = daysLeftToString(item.daysLeft)

        btn_watch.setOnClickListener { onWatchClicked(item) }
        btn_download.setOnClickListener { onDownloadClicked(item) }
    }
}