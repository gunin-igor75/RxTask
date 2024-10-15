package com.github.gunin_igor75.rxtask.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.gunin_igor75.rxtask.data.network.api.ApiService
import com.github.gunin_igor75.rxtask.presentation.MainViewModel

class ViewModelFactory(
    private val apiService: ApiService
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){
            MainViewModel::class.java -> MainViewModel(apiService)
            else -> throw IllegalArgumentException(
                "Create ViewModel Error for ${modelClass.name}. " +
                        "ModelClass must be ${this.javaClass.name}"
            )
        } as T
    }
}