package com.vmyan.myantrip.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming

interface Api {

    @Streaming
    @GET("Audio/audio.zip")
    fun downloadFileWithFixedUrl(): Call<ResponseBody>

}