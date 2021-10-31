package com.azimsiddiqui.stackoverflow.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azimsiddiqui.stackoverflow.R
import com.azimsiddiqui.stackoverflow.data.Question
import com.azimsiddiqui.stackoverflow.utils.getDateTime
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_banner.view.*
import kotlinx.android.synthetic.main.item_row_question.view.*
import okhttp3.internal.userAgent

class QuestionListAdapter(private var listener: QuestionItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    private var questionList = ArrayList<Question>()

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question: Question) {
            itemView.tv_question_title.text = question.title
            Glide.with(itemView.context)
                .load(question.owner.profile_image)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.dummy_user)
                .error(R.drawable.dummy_user)
                .into(itemView.owner_picture)

            itemView.name.text = question.owner.display_name

            val date= getDateTime(question.creation_date.toLong())
            itemView.txt_created_on.text = date.toString()
            itemView.parent_layout.setOnClickListener {
                listener.onClick(question.link)
            }
        }


    }

    private inner class BannerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            itemView.btn_close.setOnClickListener {
                //this will hide the view from recyclerview
                itemView.visibility= View.GONE
                itemView.layoutParams.height=0
                itemView.layoutParams.width=0

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_ONE) {
            return QuestionViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_question, parent, false)
            )
        }
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return questionList.size
    }

    fun setData(list: List<Question>) {
        questionList.clear()
        questionList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        if (itemType === VIEW_TYPE_ONE) {
            (holder as QuestionViewHolder).bind(questionList[position])
        } else {
            (holder as BannerViewHolder).bind(position)
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (position == 2) return VIEW_TYPE_TWO
        return VIEW_TYPE_ONE
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }
}