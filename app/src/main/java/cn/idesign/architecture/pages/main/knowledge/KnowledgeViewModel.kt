package cn.chinaunicom.drone.ui.main.processed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KnowledgeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "已处理 Fragment"
    }
    val text: LiveData<String> = _text
}