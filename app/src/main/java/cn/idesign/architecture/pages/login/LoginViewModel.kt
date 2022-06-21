package cn.idesign.architecture.pages.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.idesign.architecture.R
import cn.idesign.architecture.data.source.DataRepository
import cn.idesign.architecture.data.succeeded
import cn.idesign.architecture.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class LoginUiState(
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)

@HiltViewModel
class LoginViewModel @Inject internal constructor(
    private val dataRepository: DataRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage:MutableStateFlow<Int?> = MutableStateFlow(null)

    val uiState: StateFlow<LoginUiState> =
        combine(_isLoading, _userMessage) { isLoading, userMessage ->
            LoginUiState(
                isLoading = isLoading,
                userMessage = userMessage,
            )
        }.stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = LoginUiState(isLoading = false),
        )


    fun login(username: String?, password: String?) {
        if(username.isNullOrBlank()){
            showSnackbarMessage(R.string.login_username_empty)
            return
        }
        if(password.isNullOrBlank()){
            showSnackbarMessage(R.string.login_password_empty)
            return
        }
        viewModelScope.launch {
            val loginResult = dataRepository.login(username, password)
            if(loginResult.succeeded){
                showSnackbarMessage(R.string.login_success)
            }else{
                showSnackbarMessage(R.string.login_error)
            }
        }
    }


    fun snackbarMessageShown() {
        _userMessage.value = null
    }

    private fun showSnackbarMessage(message: Int) {
        _userMessage.value = message
    }

}