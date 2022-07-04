package cn.idesign.architecture.data.source

import androidx.paging.PagingData
import cn.idesign.architecture.data.vo.Article
import kotlinx.coroutines.flow.Flow

interface PageDataSource {
    fun getPagingData(): Flow<PagingData<Article>>
}