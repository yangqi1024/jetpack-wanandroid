package cn.idesign.architecture.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.idesign.architecture.databinding.NetworkStateItemBinding

class FooterAdapter(val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.ViewHolder>() {
    class ViewHolder(val binding: NetworkStateItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        val binding = holder.binding
        when (loadState) {
            is LoadState.Error -> {
                binding.progressBar.visibility = View.GONE
                binding.retryButton.visibility = View.VISIBLE
                binding.retryButton.text = "加载失败，点击重试～"
                binding.retryButton.setOnClickListener {
                    retry()
                }
            }
            is LoadState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.retryButton.visibility = View.VISIBLE
                binding.retryButton.text = "加载中"
            }
            is LoadState.NotLoading -> {
                binding.progressBar.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding: NetworkStateItemBinding =
            NetworkStateItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ViewHolder(binding)
    }
}
