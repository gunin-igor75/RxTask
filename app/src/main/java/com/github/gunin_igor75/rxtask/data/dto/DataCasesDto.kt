package com.github.gunin_igor75.rxtask.data.dto

import com.google.gson.annotations.SerializedName

data class DataCasesDto(
    @SerializedName("Id") val id: String,
    @SerializedName("Title") val title: String,
)
