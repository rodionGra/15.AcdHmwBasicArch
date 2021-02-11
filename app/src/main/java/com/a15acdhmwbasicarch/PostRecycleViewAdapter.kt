package com.a15acdhmwbasicarch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a15acdhmwbasicarch.databinding.BannedPostItemRecycleViewBinding
import com.a15acdhmwbasicarch.databinding.StandardPostItemRecycleViewBinding
import com.a15acdhmwbasicarch.presentation.BannedUserPostUiModel
import com.a15acdhmwbasicarch.presentation.PostUiModel
import com.a15acdhmwbasicarch.presentation.StandardPostUiModel

class PostUiModelDiffCallbackItem : DiffUtil.ItemCallback<PostUiModel>() {

    override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel) = oldItem == newItem
}

class PostRecycleViewAdapter : ListAdapter<PostUiModel, RecyclerView.ViewHolder>(PostUiModelDiffCallbackItem()) {

    enum class ViewType {
        STANDARD, BANNED
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is StandardPostUiModel -> ViewType.STANDARD.ordinal
            is BannedUserPostUiModel -> ViewType.BANNED.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewTypeEnum = ViewType.values()[viewType]

        val layout = if (viewTypeEnum == ViewType.STANDARD) {
            R.layout.standard_post_item_recycle_view
        } else {
            R.layout.banned_post_item_recycle_view
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return if (viewTypeEnum == ViewType.STANDARD) {
            ViewHolderForStandardPost(view)
        } else {
            ViewHolderForBannedPost(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderForStandardPost -> holder.bind(getItem(position) as StandardPostUiModel)
            is ViewHolderForBannedPost -> holder.bind(getItem(position) as BannedUserPostUiModel)
        }
    }

    class ViewHolderForStandardPost(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = StandardPostItemRecycleViewBinding.bind(itemView)
        fun bind(post: StandardPostUiModel) {
            binding.apply {
                tvUserId.text = post.userId
                tvTitle.text = post.title
                tvBody.text = post.body
                //todo
            }
        }
    }

    class ViewHolderForBannedPost(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = BannedPostItemRecycleViewBinding.bind(itemView)
        fun bind(post: BannedUserPostUiModel) {
            binding.apply { tvBannedText.text = post.banText }
        }
    }

}