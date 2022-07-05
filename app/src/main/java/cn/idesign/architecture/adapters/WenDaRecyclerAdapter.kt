package cn.idesign.architecture.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.idesign.architecture.data.vo.Article
import cn.idesign.architecture.databinding.LayoutWendaItemBinding
import cn.idesign.architecture.utils.ImageLoader

class WenDaRecyclerAdapter(val click: (article: Article) -> Unit) :
    PagingDataAdapter<Article, WenDaViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WenDaViewHolder {
        return WenDaViewHolder(
            LayoutWendaItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WenDaViewHolder, position: Int) {
        getItem(position)?.let { article ->
            holder.bindData(article)
            holder.binding.root.setOnClickListener {
                click(article)
            }
        }
    }

    companion object {
        //因为Paging 3在内部会使用DiffUtil来管理数据变化，所以这个COMPARATOR是必须的
        private val COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class WenDaViewHolder(val binding: LayoutWendaItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bindData(item: Article) {
        binding.tvTitle.text = item.title
        binding.tvZan.text = item.zan.toString()
        binding.tvShareUser.text = getUser(item)
        binding.tvNiceDate.text = item.niceDate
        binding.tvTagName.text = if (item.tags.isEmpty()) "" else item.tags[0].name
        binding.tvTagName.visibility =
            if (item.tags.isEmpty()) View.GONE else View.VISIBLE

        ImageLoader.getInstance().loadCircle(
            binding.ivAvatar,
            "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
        )
    }

    fun getUser(item: Article?): String {
        if (item == null) {
            return ""
        }
        if (item.shareUser.isNotBlank()) {
            return "分享者: ${item.shareUser}"
        }
        if (item.author.isNotBlank()) {
            return "作者: ${item.author}"
        }
        return ""
    }
}

