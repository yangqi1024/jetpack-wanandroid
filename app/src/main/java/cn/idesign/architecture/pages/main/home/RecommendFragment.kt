package cn.idesign.architecture.pages.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import cn.idesign.architecture.adapters.FooterAdapter
import cn.idesign.architecture.adapters.RecommendRecyclerAdapter
import cn.idesign.architecture.databinding.LayoutRecommendBinding
import cn.idesign.architecture.pages.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RecommendFragment : Fragment() {
    private val viewModel: RecommendViewModel by viewModels()
    private var _binding: LayoutRecommendBinding? = null

    private val binding get() = _binding!!
    private val adapter: RecommendRecyclerAdapter = RecommendRecyclerAdapter {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.KEY_URL, it.link)
        startActivity(intent)
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter.withLoadStateFooter(FooterAdapter {
            adapter.retry()
        })

        binding.swiperRefresh.setOnRefreshListener {
            adapter.refresh()
            viewModel.getBanner()
        }


        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swiperRefresh.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getRecommendData().collectLatest {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.getBanner().collect {
                adapter.addBannerList(it)
                adapter.notifyItemChanged(0)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}