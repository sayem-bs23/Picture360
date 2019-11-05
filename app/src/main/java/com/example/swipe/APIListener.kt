package com.example.swipe

import com.example.swipe.content.Picture

interface APIListener {
    fun onAPICalled()
    fun onSuccess(pictureList:ArrayList<Picture>)
    fun onFailure(message:String)
    fun onAPIConsumed()
}