package com.denis.testtask

import androidx.recyclerview.widget.RecyclerView
import com.denis.testtask.databinding.ItemCommentBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var comments: List<Comment>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount(): Int = comments.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.binding.apply {
            val comment = comments[position]
            tvName.text = comment.name
            tvEmail.text = comment.email
            tvBody.text = comment.body
        }
    }
}