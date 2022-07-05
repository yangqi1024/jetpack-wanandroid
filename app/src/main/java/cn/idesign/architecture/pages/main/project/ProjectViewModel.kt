package cn.idesign.architecture.pages.main.project

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cn.idesign.architecture.data.source.DataRepository
import cn.idesign.architecture.data.vo.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getProjectData(): Flow<PagingData<Article>> =
        dataRepository.getProjectData().cachedIn(viewModelScope)

}