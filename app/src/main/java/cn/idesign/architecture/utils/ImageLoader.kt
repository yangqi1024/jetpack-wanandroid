package cn.idesign.architecture.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader private constructor() {

    fun load(view: ImageView, url: String) {
        Glide
            .with(view)
            .load(url)
            .into(view);
    }

    fun loadCircle(view: ImageView, url: String) {
        Glide
            .with(view)
            .load(url)
            .circleCrop()
            .into(view);
    }


    companion object {

        @Volatile
        private var instance: ImageLoader? = null

        fun getInstance(): ImageLoader {
            return instance ?: synchronized(this) {
                instance ?: init().also { instance = it }
            }
        }

        private fun init(): ImageLoader {
            return ImageLoader()
        }
    }


}