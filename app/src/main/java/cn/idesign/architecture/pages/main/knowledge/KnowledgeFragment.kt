package cn.idesign.architecture.pages.main.knowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cn.chinaunicom.drone.ui.main.processed.KnowledgeViewModel
import cn.idesign.architecture.databinding.FragmentKnowledgeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KnowledgeFragment : Fragment() {

    private var _binding: FragmentKnowledgeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val knowledgeViewModel =
            ViewModelProvider(this).get(KnowledgeViewModel::class.java)

        _binding = FragmentKnowledgeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textProcessed
        knowledgeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}