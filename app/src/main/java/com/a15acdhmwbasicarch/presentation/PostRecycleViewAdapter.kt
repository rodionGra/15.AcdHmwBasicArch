package com.a15acdhmwbasicarch.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.databinding.BannedPostItemRecycleViewBinding
import com.a15acdhmwbasicarch.databinding.StandardPostItemRecycleViewBinding


class PostUiModelDiffCallbackItem : DiffUtil.ItemCallback<PostUiModel>() {
    override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel) =
        oldItem.postId == newItem.postId

    override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel) = oldItem == newItem
}

class PostRecycleViewAdapter :
    ListAdapter<PostUiModel, RecyclerView.ViewHolder>(PostUiModelDiffCallbackItem()) {

    enum class ViewType { STANDARD, BANNED }

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
            val strUserId =
                String.format(itemView.resources.getString(R.string.user_id), post.userId)

            binding.apply {
                tvUserId.text = strUserId
                tvTitle.text = post.title
                tvBody.text = post.body
                standardPostItemContainer.setBackgroundColor(post.backgroundColor)
                if (post.hasWarning) {
                    tvWarningText.visibility = View.VISIBLE
                } else {
                    tvWarningText.visibility = View.GONE
                }
            }
        }
    }

    class ViewHolderForBannedPost(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = BannedPostItemRecycleViewBinding.bind(itemView)
        fun bind(post: BannedUserPostUiModel) {
            val stringWithUserId: String =
                String.format(itemView.resources.getString(R.string.ban), post.userId)
            binding.apply { tvBannedText.text = stringWithUserId }
        }
    }

}