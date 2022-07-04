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
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventList = MutableLiveData<String>().apply {
        value = "待处理 Fragment"
    }
    val eventList: LiveData<String> = _eventList

    fun getPagingData(): Flow<PagingData<Article>> =
        dataRepository.getPagingData().cachedIn(viewModelScope)

    fun getBanner(): Flow<List<Banner>> =
        dataRepository.getHomeBanner()
}