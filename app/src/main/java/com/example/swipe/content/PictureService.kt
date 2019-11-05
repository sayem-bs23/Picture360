package com.example.swipe.content


import retrofit2.http.GET

interface PictureService {
    @GET("v2/list/")
    fun getAllPicture(): retrofit2.Call<List<Picture>>
    fun getSinglePicture(): retrofit2.Callback<Picture>
}

