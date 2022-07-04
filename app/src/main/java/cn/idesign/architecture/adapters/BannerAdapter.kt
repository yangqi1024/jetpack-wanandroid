package cn.idesign.architecture.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.idesign.architecture.data.vo.Banner
import cn.idesign.architecture.databinding.LayoutBannerItemBinding
import cn.idesign.architecture.utils.ImageLoader


class BannerAdapter(val data: List<Banner>) : RecyclerView.Adapter<BannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding: LayoutBannerItemBinding =
            LayoutBannerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = data[position]
        holder.bindData(banner)
    }

    override fun getItemCount(): Int = data.size
}

class BannerViewHolder(val binding: LayoutBannerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: Banner) {
        binding.tvTitle.text = item.title
        ImageLoader.getInstance().load(binding.ivImage, item.imagePath)
    }
}
