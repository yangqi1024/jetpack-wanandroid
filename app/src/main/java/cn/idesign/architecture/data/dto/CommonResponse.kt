package cn.idesign.architecture.data.dto

class CommonResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String,
){
    fun success() = errorCode == 0
}