package com.github.gunin_igor75.rxtask.presentation.utils

import com.github.gunin_igor75.rxtask.presentation.model.ListItem
import com.github.gunin_igor75.rxtask.presentation.model.UiCase


object ItemDffUtilCallback : BaseDiffUtilItemCallback() {
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        if (oldItem is UiCase && newItem is UiCase) {
            return oldItem == newItem
        }
        return super.areContentsTheSame(oldItem, newItem)
    }
}
