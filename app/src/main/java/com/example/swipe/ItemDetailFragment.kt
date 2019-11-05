package com.example.swipe

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swipe.content.PictureContent
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_item_detail.*

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_detail.*
import kotlinx.android.synthetic.main.item_list_content.*


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: PictureContent.Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val author = arguments?.getString("author")
        activity?.toolbar_layout?.title = author
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUri  = arguments?.getString("url")
        Picasso.get().load(imageUri).into(imageViewDetail)

    }


}
