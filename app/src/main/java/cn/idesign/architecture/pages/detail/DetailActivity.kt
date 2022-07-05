package cn.idesign.architecture.pages.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import cn.idesign.architecture.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra(KEY_URL)
        webView = WebView(this)
        webView.loadUrl(url!!)
        val layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        webView.layoutParams = layoutParams
        binding.webContainer.addView(webView)
        initWebSetting()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSetting() {
        val settings = webView.settings
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                // 目前拨打电话，和唤起外部第三方应用返回true，其他加载http网址的情况均返回false
                if (request!!.url.toString().startsWith("tel:")) {
                    val intent = Intent(Intent.ACTION_VIEW, request.url)
                    startActivity(intent)
                    return true
                }
                return false
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressBar.isVisible = newProgress != 100
                binding.progressBar.progress = newProgress
            }
        }
    }

    companion object {
        val KEY_URL = "web_url"
    }
}