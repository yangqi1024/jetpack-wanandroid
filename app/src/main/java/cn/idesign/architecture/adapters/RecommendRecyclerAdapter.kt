package cn.idesign.architecture.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.idesign.architecture.data.vo.Article
import cn.idesign.architecture.data.vo.Banner
import cn.idesign.architecture.databinding.LayoutArticleItemBinding
import cn.idesign.architecture.databinding.LayoutBannerBinding

class RecommendRecyclerAdapter(val click: (article: Article) -> Unit) :
    PagingDataAdapter<Article, RecyclerView.ViewHolder>(COMPARATOR) {
    private var bannerList: List<Banner> = mutableListOf()

    fun addBannerList(list: List<Banner>) {
        bannerList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (TYPE_BANNER == viewType) {
            return BannerItemViewHolder(
                LayoutBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        return RecommendViewHolder(
            LayoutArticleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)

        if (TYPE_BANNER == itemViewType) {
            (holder as BannerItemViewHolder).bindData(bannerList)
        } else {
            val item = getItem(position - 1)?.let { article ->
                val recommendViewHolder = holder as RecommendViewHolder
                recommendViewHolder.bindData(article)
                recommendViewHolder.binding.root.setOnClickListener {
                    click(article)
                }
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_BANNER
        }
        return TYPE_ARTICLE
    }


    companion object {
        const val TYPE_BANNER = 0
        const val TYPE_ARTICLE = 1

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

class RecommendViewHolder(val binding: LayoutArticleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bindData(item: Article) {
        binding.tvTitle.text = item.title
        binding.tvDesc.text = item.desc
        binding.tvShareUser.text = getUser(item)
        binding.tvNiceDate.text = "时间: ${item.niceDate}"
        binding.tvTagName.text = if (item.tags.isEmpty()) "" else item.tags[0].name
        binding.tvTagName.visibility =
            if (item.tags.isEmpty()) View.GONE else View.VISIBLE
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

class BannerItemViewHolder(val binding: LayoutBannerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: List<Banner>) {
        binding.root.adapter = BannerAdapter(item)
    }
}