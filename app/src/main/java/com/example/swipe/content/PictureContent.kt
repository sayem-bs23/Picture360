package com.example.swipe.content

import android.util.Log
import android.widget.ArrayAdapter
import com.example.swipe.APIListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import kotlin.collections.ArrayList


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
class PictureContent(private val apiListener: APIListener) {

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class Item(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }

     fun getData(){
         apiListener.onAPICalled()
        val call = RetrofitSingleton.instanceAllPicture.getAllPicture()

        call.enqueue(object : Callback<List<Picture>> {
            override fun onResponse(
                call: Call<List<Picture>>,
                response: Response<List<Picture>>
            ) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                if (response.code() == 200) {

                    val pictureResponse = response.body()!!
                    Log.d("author", pictureResponse[0].author!!)

                    val pictureList = ArrayList<Picture>()
                    for(i in pictureResponse as ArrayList<Picture>){

                        if(i!!.width!!.toInt() < 4000){
                            pictureList.add(i)
                        }
                    }
                    apiListener.onSuccess(pictureList)
                }
            }

            override fun onFailure(call: Call<List<Picture>>?, t: Throwable?) {
                Log.d("err_on_resp", t?.message.toString())
                apiListener.onFailure("failed")
            }
        })


    }
}
