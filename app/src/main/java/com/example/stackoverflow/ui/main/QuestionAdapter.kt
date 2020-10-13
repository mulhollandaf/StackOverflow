package com.example.stackoverflow.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.stackoverflow.R
import com.example.stackoverflow.databinding.ListItemBinding

class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    var questionInfos: List<QuestionInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(parent)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        questionInfos[position].also { questionInfo ->
            holder.binding.apply {
                question = questionInfo
            }
        }
    }

    class QuestionViewHolder(parent: ViewGroup) :
        BindingViewHolder<ListItemBinding>(R.layout.list_item, parent) {

    }

    override fun getItemCount(): Int {
        return questionInfos.size
    }
}

open class BindingViewHolder<out T : ViewDataBinding>
private constructor(val binding: T) : RecyclerView.ViewHolder(binding.root) {
    constructor(@LayoutRes layoutId: Int, parent: ViewGroup): this(inflate<T>(layoutId, parent))

    companion object {
        @Suppress("NOTHING_TO_INLINE")
        inline fun<T: ViewDataBinding> inflate(@LayoutRes layoutId: Int, parent: ViewGroup): T {
            return DataBindingUtil.inflate(parent.inflater(), layoutId, parent, false)
        }
    }
}

fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(context)
