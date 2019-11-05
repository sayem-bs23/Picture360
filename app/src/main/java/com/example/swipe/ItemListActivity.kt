package com.example.swipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager

import com.example.swipe.content.PictureContent
import com.example.swipe.content.Picture

import com.google.gson.Gson
import android.widget.Toast
import android.net.ConnectivityManager
import android.content.DialogInterface

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_WIRELESS_SETTINGS
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity(), APIListener {

    private val numberOfColumn = 3
    private val pictureContent = PictureContent(this)

    private var pictureAdapter: PictureAdapter = PictureAdapter()

    fun init(){
        if(!isOnline()){
            showNoConnectionDialog(this)

        }
        else {

            pictureContent.getData()
        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_item_list)
        setSupportActionBar(toolbar)
        toolbar.title = title
        setupRecyclerView(item_list)
        init()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = GridLayoutManager(this, numberOfColumn)
        pictureAdapter.itemClickListener = object: ItemClickListener<Picture> {
            override fun onItemClick(position: Int, item: Picture) {
                Log.d("cl", "position: $position ")

                val gson = Gson()
                val intent = Intent(recyclerView.context, ItemDetailActivity::class.java).apply {
                    putExtra("obj", gson.toJson(item))
                }
                startActivity(intent)
            }
        }
        recyclerView.adapter = pictureAdapter

    }
    override fun onAPICalled() {
        progressBar.visibility = View.VISIBLE
        Log.d("api_called", "yes")
    }

    override fun onSuccess(pictureList: ArrayList<Picture>) {
        Log.d("api_called", "onSuccess")


        pictureAdapter.setData(pictureList)
        item_list.adapter = pictureAdapter
        progressBar.visibility = View.GONE

    }

    override fun onFailure(message: String) {
        Log.d("api_called", "onFailure")
        progressBar.visibility = View.GONE
//        if(!isOnline()){
//            showNoConnectionDialog(this)
//        }

    }

    override fun onAPIConsumed() {
        Log.d("api_called", "onApiConsumed")
    }

    fun isOnline(): Boolean {
        val conMgr =
            applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo

        if (netInfo == null || !netInfo.isConnected || !netInfo.isAvailable) {
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun showNoConnectionDialog(ctx1: Context) {

        val builder = AlertDialog.Builder(ctx1)
        builder.setCancelable(true)
        builder.setMessage("No connection")
        builder.setTitle("Settings")
        builder.setPositiveButton("Ok",
            DialogInterface.OnClickListener {
                    dialog, which ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                    onPause()
                    startActivityForResult(panelIntent, 545)
                }
                else {
                    onPause()
                    ctx1.startActivity(Intent(ACTION_WIRELESS_SETTINGS))
                }
            })

        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> return@OnClickListener })

        builder.setOnCancelListener(DialogInterface.OnCancelListener { return@OnCancelListener })

        builder.show()
    }

    override fun onResume() {
        super.onResume()
        if(isOnline())
        {
            init()
            Log.d("onResumeCalled", "called")
        }

    }


}
