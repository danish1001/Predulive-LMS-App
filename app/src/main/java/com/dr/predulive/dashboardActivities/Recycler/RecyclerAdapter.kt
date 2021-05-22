package com.dr.apigallery2.Recycler

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dr.predulive.R
import com.dr.predulive.dashboardActivities.Recycler.IndividualData


public class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    var context: Context
    var list: ArrayList<IndividualData>



    constructor(context: Context, list: ArrayList<IndividualData>) {
        this.context = context
        this.list = list
    }

    public class ViewHolder: RecyclerView.ViewHolder {
        var name: TextView
        var email: TextView
        var about: TextView
        var itemImage: ImageView

        constructor(itemView: View) : super(itemView) {
            name = itemView.findViewById(R.id.itemName)
            email = itemView.findViewById(R.id.itemEmail)
            about = itemView.findViewById(R.id.itemAbout)
            itemImage = itemView.findViewById(R.id.itemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view: View = layoutInflater.inflate(R.layout.item, parent, false)
        var viewHolder: ViewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text =  list[position].getName()
        holder.email.text = "Email: " + list[position].getEmail()
        holder.about.text = "About: " + list[position].getAbout()

        Glide.with(context)
                .load(list[position].getImageUrl())
                .centerCrop()
                .encodeQuality(10)
                .placeholder(R.drawable.loading_layout)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemImage)
    }
}