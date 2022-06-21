package cn.idesign.architecture.pages.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import cn.idesign.architecture.R
import cn.idesign.architecture.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        Handler(Looper.myLooper()!!).postDelayed({
            binding.motionLayout.transitionToEnd()
        }, 300)

        binding.apply {

            button.setOnClickListener {
                val username = etUsername.text?.toString()
                val password = etPassword.text?.toString()
                viewModel.login(username, password)
            }
        }
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                uiState.userMessage?.let { message ->
                    val snackbarText = resources.getString(message)
                    Snackbar.make(binding.etUsername, snackbarText, Snackbar.LENGTH_SHORT).show()
                    viewModel.snackbarMessageShown()
                }
            }
        }
    }
}