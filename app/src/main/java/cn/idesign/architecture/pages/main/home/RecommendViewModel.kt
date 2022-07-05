package cn.idesign.architecture.pages.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cn.idesign.architecture.data.source.DataRepository
import cn.idesign.architecture.data.vo.Article
import cn.idesign.architecture.data.vo.Banner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getRecommendData(): Flow<PagingData<Article>> =
        dataRepository.getRecommendData().cachedIn(viewModelScope)

    fun getBanner(): Flow<List<Banner>> =
        dataRepository.getHomeBanner()

    fun getWenDaData(): Flow<PagingData<Article>> =
        dataRepository.getWenDaData().cachedIn(viewModelScope)
}