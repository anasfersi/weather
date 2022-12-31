package com.example.weatherapp.ui.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (url!=null) Picasso.get().load(url).into(view)
}