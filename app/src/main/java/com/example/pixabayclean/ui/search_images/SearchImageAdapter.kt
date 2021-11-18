package com.example.pixabayclean.ui.search_images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabayclean.databinding.ItemListImageBinding
import com.example.pixabayclean.domain.model.Image

class SearchImageAdapter(private val clickListener: SearchImageClickListener) :
    PagingDataAdapter<Image, RecyclerView.ViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoViewHolder(
            ItemListImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotoViewHolder).bind(clickListener, getItem(position)!!)
    }

    class PhotoViewHolder(private val binding: ItemListImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bind(listener: SearchImageClickListener, item: Image) {
            binding.apply {
                clickListener = listener
                image = item
                executePendingBindings()
            }
        }
    }

}