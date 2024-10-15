package com.github.gunin_igor75.rxtask.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.github.gunin_igor75.rxtask.presentation.model.ListItem

open class BaseDiffUtilItemCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.id == newItem.id
    }
}