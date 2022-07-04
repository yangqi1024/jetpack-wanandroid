package cn.idesign.architecture.data.dto

class PageData<T>(
    val datas: T,
    val curPage: Int,
    val offset: String,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
)