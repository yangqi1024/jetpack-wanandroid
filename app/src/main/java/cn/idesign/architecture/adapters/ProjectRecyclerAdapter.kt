package cn.idesign.architecture.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.idesign.architecture.data.vo.Article
import cn.idesign.architecture.databinding.LayoutProjectItemBinding
import cn.idesign.architecture.utils.ImageLoader

class ProjectRecyclerAdapter(val click: (article: Article) -> Unit) :
    PagingDataAdapter<Article, ProjectViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder(
            LayoutProjectItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
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

class ProjectViewHolder(val binding: LayoutProjectItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bindData(item: Article) {
        binding.tvAuthor.text = getUser(item)
        binding.tvName.text = item.title
        ImageLoader.getInstance().loadCircle(
            binding.ivAvatar,
            "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
        )
        ImageLoader.getInstance().load(
            binding.ivCover,
            item.envelopePic
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

