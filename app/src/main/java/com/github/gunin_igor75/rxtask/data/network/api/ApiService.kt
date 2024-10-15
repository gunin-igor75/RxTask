package com.github.gunin_igor75.rxtask.data.network.api

import io.reactivex.Single
import retrofit2.http.GET
import com.github.gunin_igor75.rxtask.data.dto.ContainerResponseCases

interface ApiService {

    @GET("cases")
    fun getCases(): Single<ContainerResponseCases>
}