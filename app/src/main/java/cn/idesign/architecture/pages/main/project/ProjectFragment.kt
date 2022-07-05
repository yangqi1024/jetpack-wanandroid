package cn.idesign.architecture.pages.main.project

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
import cn.idesign.architecture.adapters.ProjectRecyclerAdapter
import cn.idesign.architecture.databinding.FragmentProjectBinding
import cn.idesign.architecture.pages.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProjectFragment : Fragment() {
    private val viewModel: ProjectViewModel by viewModels()
    private var _binding: FragmentProjectBinding? = null
    private val binding get() = _binding!!
    private val adapter: ProjectRecyclerAdapter = ProjectRecyclerAdapter {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.KEY_URL, it.link)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectBinding.inflate(inflater, container, false)
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
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swiperRefresh.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {

            viewModel.getProjectData().collectLatest {
                adapter.submitData(it)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}