package com.github.gunin_igor75.rxtask.data.dto

import com.google.gson.annotations.SerializedName

data class ContainerResponseCases(
    @SerializedName("Error") val error: String?,
    @SerializedName("Data") val dataCasesDto: List<DataCasesDto>
)
