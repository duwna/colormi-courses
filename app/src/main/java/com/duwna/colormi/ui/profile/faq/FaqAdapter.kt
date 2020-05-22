package com.duwna.colormi.ui.profile.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duwna.colormi.R
import com.duwna.colormi.models.database.Faq
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news.view.*

class FaqAdapter : ListAdapter<Faq, FaqViewHolder>(SearchDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val containerView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faq, parent, false)
        return FaqViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SearchDiffCallBack : DiffUtil.ItemCallback<Faq>() {

    override fun areItemsTheSame(oldItem: Faq, newItem: Faq): Boolean {
        return oldItem.question == newItem.question
    }

    override fun areContentsTheSame(oldItem: Faq, newItem: Faq): Boolean {
        return oldItem == newItem
    }
}

class FaqViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: Faq) = containerView.run {
        tv_question.text = item.question
        tv_answer.text = item.answer
    }
}