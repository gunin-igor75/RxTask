package com.github.gunin_igor75.rxtask.presentation.adapter

import com.github.gunin_igor75.rxtask.databinding.CardItemBinding
import com.github.gunin_igor75.rxtask.presentation.model.ListItem
import com.github.gunin_igor75.rxtask.presentation.model.UiCase
import com.github.gunin_igor75.rxtask.presentation.utils.ItemDffUtilCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class CaseAdapter(
    onClickItem: (String) -> Unit,
) : AsyncListDifferDelegationAdapter<ListItem>(ItemDffUtilCallback) {

    init {
        delegatesManager
            .addDelegate(
                uiCaseDelegate(
                    onClickItem = onClickItem,
                )
            )
    }

    private fun uiCaseDelegate(onClickItem: (String) -> Unit) =
        adapterDelegateViewBinding<UiCase, ListItem, CardItemBinding>(
            { layoutInflater, container ->
                CardItemBinding.inflate(
                    layoutInflater,
                    container,
                    false
                )
            }
        ) {
            bind {
                with(binding) {
                    textViewTitle.text = item.title
                    root.setOnClickListener {
                        onClickItem.invoke(item.id)
                    }
                }
            }
        }
}