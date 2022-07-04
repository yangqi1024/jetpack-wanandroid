package cn.idesign.architecture.utils

import com.google.gson.Gson

class GsonManager {
    companion object {

        @Volatile
        private var instance: Gson? = null

        fun getInstance(): Gson {
            return instance ?: synchronized(this) {
                instance ?: buildGson().also { instance = it }
            }
        }

        private fun buildGson(): Gson {
            return Gson()
        }
    }
}