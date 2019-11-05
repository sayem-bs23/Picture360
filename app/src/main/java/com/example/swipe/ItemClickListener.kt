package com.example.swipe

interface ItemClickListener<T> {
    fun onItemClick(position:Int, item:T)
}