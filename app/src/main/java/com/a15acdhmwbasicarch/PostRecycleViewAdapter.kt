package com.a15acdhmwbasicarch

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a15acdhmwbasicarch.presentation.PostUiModel
import com.a15acdhmwbasicarch.presentation.StandardPostUiModel

class DiffCallbackItem : DiffUtil.ItemCallback<PostUiModel>() {

    override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel) = oldItem.postId == newItem.postId

    override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel) = oldItem == newItem

}

class ExampleAdapter : ListAdapter<PostUiModel, RecyclerView.ViewHolder>(DiffCallbackItem()) {
    //не потрібно в ручну створювати масив, він уже створений під капотом
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //так як масив уже створений за нас звертаємося до нього через getItem()
        //holder.bind(getItem(position))
    }
}